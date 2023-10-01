package com.br.techroom.service.impl;

import com.br.techroom.dto.requests.RegisterRequestDTO;
import com.br.techroom.exception.InternalErrorException;
import com.br.techroom.exception.ValidationRegisterException;
import com.br.techroom.model.AccountModel;
import com.br.techroom.repository.AccountRepository;
import com.br.techroom.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 *@author Edson Rafael
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Método responsável por salvar um novo usuário no banco de dados.
     *
     * @return AccountModel
     *
     */
    @Override
    @Transactional
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
            var accountModel = modelMapper.map(account, AccountModel.class);

            return accountRepository.save(accountModel);
        } catch (DataAccessException e) {
            throw new InternalErrorException(e.getMessage());
        }
    }
}
