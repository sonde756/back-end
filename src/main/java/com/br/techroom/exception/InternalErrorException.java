package com.br.techroom.exception;

public class InternalErrorException extends RuntimeException {
    public InternalErrorException(String msg) {
        super(msg);
    }
}
