package com.br.techroom.builders.dto.request;

import com.br.techroom.dto.request.AccountRegisterRequestDTO;

public class AccountRegisterRequestDTOBuilder {

    private AccountRegisterRequestDTO accountRegisterRequestDTO;

    private AccountRegisterRequestDTOBuilder() {
        this.accountRegisterRequestDTO = new AccountRegisterRequestDTO();
    }

    public static AccountRegisterRequestDTOBuilder builder() {
        return new AccountRegisterRequestDTOBuilder();
    }

    public AccountRegisterRequestDTO build() {
        return accountRegisterRequestDTO;
    }

    public AccountRegisterRequestDTOBuilder username(String username) {
        this.accountRegisterRequestDTO.setUsername(username);
        return this;
    }

    public AccountRegisterRequestDTOBuilder email(String email) {
        this.accountRegisterRequestDTO.setEmail(email);
        return this;
    }

    public AccountRegisterRequestDTOBuilder password(String password) {
        this.accountRegisterRequestDTO.setPassword(password);
        return this;
    }

    public AccountRegisterRequestDTOBuilder confirmPassword(String password) {
        this.accountRegisterRequestDTO.setConfirmPassword(password);
        return this;
    }
}