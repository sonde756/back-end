package com.br.techroom.service.exception;

public class PasswordsIsNotEqualsException extends RuntimeException{

    private static final String ERROR = "As senhas digitadas não conferem";

    public PasswordsIsNotEqualsException(){
        super(ERROR);
    }
}