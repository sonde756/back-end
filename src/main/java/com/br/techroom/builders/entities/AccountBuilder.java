package com.br.techroom.builders.entities;

import com.br.techroom.entities.Account;
import com.br.techroom.entities.Role;

import java.time.Instant;
import java.util.List;

public class AccountBuilder {

    private Account account;

    private AccountBuilder() {
        this.account = new Account();
    }

    public static AccountBuilder builder() {
        return new AccountBuilder();
    }

    public Account build() {
        return account;
    }

    public AccountBuilder id(Long id) {
        this.account.setId(id);
        return this;
    }

    public AccountBuilder username(String username) {
        this.account.setUsername(username);
        return this;
    }

    public AccountBuilder email(String email) {
        this.account.setEmail(email);
        return this;
    }

    public AccountBuilder role(List<Role> roles) {
        this.account.setRoles(roles);
        return this;
    }

    public AccountBuilder password(String password) {
        this.account.setPassword(password);
        return this;
    }

    public AccountBuilder enabled(boolean enabled) {
        this.account.setEnabled(enabled);
        return this;
    }

    public AccountBuilder createdAt(Instant moment) {
        this.account.setCreatedAt(moment);
        return this;
    }
}