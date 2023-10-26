package com.br.techroom.builders.entities;

import com.br.techroom.entities.VerificationCode;

import java.time.LocalDateTime;

public class VerificationCodeBuilder {

    private VerificationCode verificationCode;

    private VerificationCodeBuilder() {
        this.verificationCode = new VerificationCode();
    }

    public static VerificationCodeBuilder builder() {
        return new VerificationCodeBuilder();
    }

    public VerificationCode build() {
        return verificationCode;
    }

    public VerificationCodeBuilder id(Long id) {
        this.verificationCode.setId(id);
        return this;
    }

    public VerificationCodeBuilder token(String token) {
        this.verificationCode.setToken(token);
        return this;
    }

    public VerificationCodeBuilder dateExpiration(LocalDateTime date) {
        this.verificationCode.setDateExpiration(date);
        return this;
    }
}