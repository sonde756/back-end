package com.br.techroom.dto.response;


/**
 * DTO para resposta de autenticação
 */

public class AccountLoginResponseDTO {

    private String username;
    private String token;

    public AccountLoginResponseDTO() {
    }

    public AccountLoginResponseDTO(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
