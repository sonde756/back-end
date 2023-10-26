package com.br.techroom.service.exception.structure;

import com.br.techroom.controller.handlers.GenericStandardException;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorException extends GenericStandardException {

    private List<FieldMessage> errors = new ArrayList<>();

    public ValidationErrorException(Integer status, String error, String path) {
        super(status, error, path);
    }

    public List<FieldMessage> getErrors() {
        return errors;
    }

    public void addError(FieldMessage error) {
        this.errors.add(error);
    }
}