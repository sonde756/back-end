package com.br.techroom.builders.dto.response;

import com.br.techroom.dto.response.VerificationCodeResponseDTO;

import java.time.LocalDateTime;

public class VerificationCodeResponseDTOBuilder {

    private VerificationCodeResponseDTO verificationCode;

    private VerificationCodeResponseDTOBuilder() {
        this.verificationCode = new VerificationCodeResponseDTO();
    }

    public static VerificationCodeResponseDTOBuilder builder() {
        return new VerificationCodeResponseDTOBuilder();
    }

    public VerificationCodeResponseDTO build() {
        return verificationCode;
    }

    public VerificationCodeResponseDTOBuilder token(String token) {
        this.verificationCode.setToken(token);
        return this;
    }

    public VerificationCodeResponseDTOBuilder dateExpiration(LocalDateTime date) {
        this.verificationCode.setDateExpiration(date);
        return this;
    }
}