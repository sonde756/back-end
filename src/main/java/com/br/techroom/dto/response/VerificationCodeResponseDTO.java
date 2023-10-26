package com.br.techroom.dto.response;

import java.time.LocalDateTime;

public class VerificationCodeResponseDTO {

    private String token;
    private LocalDateTime dateExpiration;

    public VerificationCodeResponseDTO() {
    }

    public VerificationCodeResponseDTO(String token, LocalDateTime dateExpiration) {
        this.token = token;
        this.dateExpiration = dateExpiration;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(LocalDateTime dateExpiration) {
        this.dateExpiration = dateExpiration;
    }
}