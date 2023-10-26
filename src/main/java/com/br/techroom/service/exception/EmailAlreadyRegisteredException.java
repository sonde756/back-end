package com.br.techroom.service.exception;

public class EmailAlreadyRegisteredException extends RuntimeException{

    private static final String ERROR = "Email já registrado";

    public EmailAlreadyRegisteredException(){
        super(ERROR);
    }
}