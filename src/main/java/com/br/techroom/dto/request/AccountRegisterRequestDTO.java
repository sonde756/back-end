package com.br.techroom.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para registro do usuário.
 */

public class AccountRegisterRequestDTO {

    @NotBlank(message = "Nome de usuário não pode ser vazio")
    @Size(max = 20, message = "Excedido limite de 20 caracteres")
    private String username;

    @NotBlank
    @Size(max = 100, message = "Excedido limite de 100 caracteres")
    @Email(message = "Email inválido")
    private String email;

    @Size(min = 6, max = 20, message = "Senha deve ter entre 6 e 20 caracteres")
    private String password;

    @NotBlank(message = "Confirmação de senha não pode ser vazio")
    private String confirmPassword;

    private String role;

    public AccountRegisterRequestDTO() {
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}