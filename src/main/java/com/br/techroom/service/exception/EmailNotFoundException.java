package com.br.techroom.service.exception;

public class EmailNotFoundException extends RuntimeException {

    private static final String ERROR = "Email não encontrado, verifique sua entrada de dados";

    public EmailNotFoundException() {
        super(ERROR);
    }
}
