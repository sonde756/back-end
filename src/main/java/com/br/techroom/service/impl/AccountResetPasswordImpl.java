package com.br.techroom.service.impl;

import com.br.techroom.dto.requests.ResetPasswordDTO;
import com.br.techroom.exception.ConfirmEmailException;
import com.br.techroom.model.AccountModel;
import com.br.techroom.model.AccountTokenPasswordReset;
import com.br.techroom.repository.AccountRepository;
import com.br.techroom.repository.AccountTokenPasswordResetRepository;
import com.br.techroom.service.AccountResetPasswordService;
import com.br.techroom.service.EmailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.br.techroom.util.TokenGenerator.generateTokenPassword;


@Service
public class AccountResetPasswordImpl implements AccountResetPasswordService {

    final
    AccountRepository accountRepository;

    final
    AccountTokenPasswordResetRepository accountTokenPasswordResetRepository;

    final
    ModelMapper modelMapper;

    final
    EmailService emailService;

    final
    PasswordEncoder passwordEncoder;

    @Autowired
    public AccountResetPasswordImpl(AccountRepository accountRepository, AccountTokenPasswordResetRepository accountTokenPasswordResetRepository, ModelMapper modelMapper, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.accountTokenPasswordResetRepository = accountTokenPasswordResetRepository;
        this.modelMapper = modelMapper;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }


    /**
     * @param email email to be validated
     */
    @Transactional
    @Override
    public void resetPassword(String email) {
        var accountModelOptional = accountRepository.findByEmail(email);
        var accountModel = accountModelOptional.orElseThrow();

        //case the user already has a token, it will be updated
        if (accountTokenPasswordResetRepository.existsByIdUser(accountModel)) {
            var tokenResetPassword = accountTokenPasswordResetRepository.findByIdUser(accountModel);
            updateAndCreatedToken(email, tokenResetPassword, accountModel);
        } else {
            //case the user does not have a token, it will be created
            var tokenResetPassword = modelMapper.map(accountModel, AccountTokenPasswordReset.class);
            updateAndCreatedToken(email, tokenResetPassword, accountModel);
        }
    }
    private void updateAndCreatedToken(String email, AccountTokenPasswordReset tokenResetPassword, AccountModel accountModel) {
        tokenResetPassword.setTokenConfirmation(generateTokenPassword());
        tokenResetPassword.setDateExpiration(LocalDateTime.now().plusMinutes(15L));
        accountTokenPasswordResetRepository.save(tokenResetPassword);
        emailService.sendEmailPasswordReset(accountModel.getUsername(), email, tokenResetPassword.getTokenConfirmation());
    }


    /**
     * @param token    token to be validated
     * @param password new password
     */
    @Transactional
    @Override
    public void changePassword(String token, ResetPasswordDTO password) {
        var accountToken =
                Optional.ofNullable(accountTokenPasswordResetRepository.findByTokenConfirmation(token))
                        .orElseThrow(() -> new ConfirmEmailException("Token não encontrado ou inválido"));

        var accountModel = accountToken.getIdUser();

        if (!password.getPassword().equals(password.getConfirmPassword())) {
            throw new ConfirmEmailException("As senhas não conferem");
        }
        if (accountToken.getDateExpiration().isBefore(LocalDateTime.now())) {
            throw new ConfirmEmailException("Token expirado, solicite um novo token.");
        }

        accountModel.setPassword(passwordEncoder.encode(password.getPassword()));
        accountRepository.save(accountModel);
        accountTokenPasswordResetRepository.delete(accountToken);
        emailService.sendEmailPasswordResetSucess(accountModel.getEmail());
    }


}
