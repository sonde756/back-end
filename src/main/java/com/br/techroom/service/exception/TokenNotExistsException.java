package com.br.techroom.service.exception;

public class TokenNotExistsException extends RuntimeException{

    private static final String ERROR = "O token inserido é inválido";

    public TokenNotExistsException(){
        super(ERROR);
    }
}
