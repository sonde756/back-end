package com.br.techroom.service.impl;

import com.br.techroom.constants.StatusConstants;
import com.br.techroom.exception.ConfirmEmailException;
import com.br.techroom.model.AccountModel;
import com.br.techroom.model.AccountTokenConfirmEmail;
import com.br.techroom.repository.AccountRepository;
import com.br.techroom.repository.AccountTokenConfirmEmailRepository;
import com.br.techroom.service.AccountTokenConfirmEmailService;
import com.br.techroom.service.EmailService;
import com.br.techroom.service.StatusService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.br.techroom.util.TokenGenerator.generateToken;

/**
 * @Author Edson
 */
@Service
public class AccountTokenConfirmEmailServiceImpl implements AccountTokenConfirmEmailService {

    final AccountTokenConfirmEmailRepository accountTokenEmailRepository;
    final ModelMapper modelMapper;
    final PasswordEncoder passwordEncoder;
    final AccountRepository accountRepository;
    final StatusService statusService;
    final EmailService emailService;

    @Autowired
    public AccountTokenConfirmEmailServiceImpl(AccountTokenConfirmEmailRepository accountTokenEmailRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, AccountRepository accountRepository, StatusService statusService, EmailService emailService) {
        this.accountTokenEmailRepository = accountTokenEmailRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.accountRepository = accountRepository;
        this.statusService = statusService;
        this.emailService = emailService;
    }

    /**
     * responsible method for saving token for email confirmation in the database
     *
     * @param account the account to be saved
     */

    @Override
    public void save(AccountModel account) {
        try {
            var token = modelMapper.map(account, AccountTokenConfirmEmail.class);
            token.setTokenConfirmation(generateToken());
            token.setDateExpiration(LocalDateTime.now().plusDays(1));
            emailService.sendEmailConfirmation(account.getUsername(), account.getEmail(), token.getTokenConfirmation());

            accountTokenEmailRepository.save(token);
        } catch (Exception e) {
            throw new ConfirmEmailException("Erro ao enviar o e-mail de confirmação.");
        }

    }

    /**
     * responsible method for confirming the email with the token
     *
     * @param token the token to be confirmed
     */

    @Override
    public void confirmEmailWithToken(String token) {
        var accountToken =
                Optional.ofNullable(accountTokenEmailRepository.findByTokenConfirmation(token))
                        .orElseThrow(() -> new ConfirmEmailException("Token não encontrado ou inválido"));

        var account = accountRepository.getReferenceById(accountToken.getIdUser().getIdUser());

        if (accountToken.getDateExpiration().isBefore(LocalDateTime.now())) {
            accountToken.setTokenConfirmation(generateToken());
            accountToken.setDateExpiration(LocalDateTime.now().plusDays(1));
            emailService.sendEmailConfirmation(account.getUsername(), account.getEmail(), accountToken.getTokenConfirmation());
            accountTokenEmailRepository.save(accountToken);

            throw new ConfirmEmailException("Token expirado, um novo token foi enviado para o seu e-mail");
        }

        if (account.getStatus().equals(statusService.findByStatus(StatusConstants.TYPE_USER, StatusConstants.USER_ACTIVE))) {
            throw new ConfirmEmailException("Conta já ativada");
        }

        account.setStatus(statusService.findByStatus(StatusConstants.TYPE_USER, StatusConstants.USER_ACTIVE));
        accountRepository.save(account);
        accountTokenEmailRepository.delete(accountToken);

    }


}
