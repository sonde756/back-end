package com.br.techroom.service.exception;

public class TokenHasExpiredException extends RuntimeException {

    private static final String ERROR =
            "O token inserido está expirado, um novo token foi enviado para seu email";

    public TokenHasExpiredException() {
        super(ERROR);
    }
}