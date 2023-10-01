package com.br.techroom.service.impl;

import com.br.techroom.dto.requests.RegisterRequestDTO;
import com.br.techroom.exception.InternalErrorException;
import com.br.techroom.exception.ValidationRegisterException;
import com.br.techroom.model.AccountModel;
import com.br.techroom.repository.AccountRepository;
import com.br.techroom.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *@author Edson Rafael
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Método responsável por salvar um novo usuário no banco de dados.
     *
     * @return AccountModel
     *
     */
    @Override
    public AccountModel save(RegisterRequestDTO account) {
        try {

            if (!account.getPassword().equals(account.getConfirmPassword())) {
                throw new ValidationRegisterException("Senhas não conferem");
            }
            if (accountRepository.existsByUsername(account.getUsername())) {
                throw new ValidationRegisterException("Nome de usuário já existe");
            }
            if (accountRepository.existsByEmail(account.getEmail())) {
                throw new ValidationRegisterException("Email já existe");
            }

            account.setPassword(passwordEncoder.encode(account.getPassword()));
            var accountModel = new AccountModel(null,account.getUsername(),
                    account.getEmail().toLowerCase(), account.getPassword());

            return accountRepository.save(accountModel);
        } catch (DataAccessException e) {
            throw new InternalErrorException(e.getMessage());
        }
    }
}
