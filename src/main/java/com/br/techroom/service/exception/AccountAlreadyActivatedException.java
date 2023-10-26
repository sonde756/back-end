package com.br.techroom.service.exception;

public class AccountAlreadyActivatedException extends RuntimeException{

    private static final String ERROR = "Essa conta já está ativada";

    public AccountAlreadyActivatedException(){
        super(ERROR);
    }
}
