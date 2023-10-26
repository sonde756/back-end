package com.br.techroom.service.exception;

public class InvalidEnumValueException extends RuntimeException {

    private static final String ERROR = "A role inserida não é válida";

    public InvalidEnumValueException(String value, String enumType, String listEnums) {
        super(String.format(
                "A role inserida: %s não é válida. Valores válidos: %s", value, listEnums));
    }
}