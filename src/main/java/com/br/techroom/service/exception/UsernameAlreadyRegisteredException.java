package com.br.techroom.service.exception;

public class UsernameAlreadyRegisteredException extends RuntimeException{
    private static final String ERROR = "Username já registrado";

    public UsernameAlreadyRegisteredException(){
        super(ERROR);
    }
}