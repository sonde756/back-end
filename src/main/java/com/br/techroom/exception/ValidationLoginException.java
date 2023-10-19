package com.br.techroom.exception;

public class ValidationLoginException extends RuntimeException {
    public ValidationLoginException(String message) {
        super(message);
    }
}
