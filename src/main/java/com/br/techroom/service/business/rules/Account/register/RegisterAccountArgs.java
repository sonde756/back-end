package com.br.techroom.service.business.rules.Account.register;

import com.br.techroom.dto.request.AccountRegisterRequestDTO;
import com.br.techroom.repository.AccountRepository;

public class RegisterAccountArgs {

    private AccountRegisterRequestDTO dto;
    private AccountRepository repository;

    public RegisterAccountArgs(AccountRegisterRequestDTO dto, AccountRepository repository) {
        this.dto = dto;
        this.repository = repository;
    }

    public AccountRegisterRequestDTO getDto() {
        return dto;
    }

    public AccountRepository getRepository() {
        return repository;
    }
}