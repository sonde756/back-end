package com.br.techroom.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

/*
 * DTO para autenticação do usuário
 */

public class AccountLoginRequestDTO {

    @NotBlank(message = "Deve informar o username ou email")
    private String username;

    @NotBlank(message = "Deve informar a senha")
    private String password;

    public AccountLoginRequestDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        AccountLoginRequestDTO that = (AccountLoginRequestDTO) object;
        return Objects.equals(username, that.username) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}