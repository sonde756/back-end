package com.br.techroom.controller.handlers;


import com.br.techroom.service.exception.*;
import com.br.techroom.service.exception.structure.FieldMessage;
import com.br.techroom.service.exception.structure.ValidationErrorException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandlerExceptionController {


    @ExceptionHandler(TokenNotExistsException.class)
    public ResponseEntity<GenericStandardException> tokenNotExists(TokenNotExistsException e, HttpServletRequest request) {
        return genericHandlingException(e, HttpStatus.BAD_REQUEST.value(), request.getRequestURI());
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<GenericStandardException> entityNotFound(EmailNotFoundException e, HttpServletRequest request) {
        return genericHandlingException(e, HttpStatus.NOT_FOUND.value(), request.getRequestURI());
    }

    @ExceptionHandler(TokenHasExpiredException.class)
    public ResponseEntity<GenericStandardException> tokenHasExpired(TokenHasExpiredException e, HttpServletRequest request) {
        return genericHandlingException(e, HttpStatus.GONE.value(), request.getRequestURI());
    }

    @ExceptionHandler(AccountAlreadyActivatedException.class)
    public ResponseEntity<GenericStandardException> accountAlreadyActivated(AccountAlreadyActivatedException e, HttpServletRequest request) {
        return genericHandlingException(e, HttpStatus.CONFLICT.value(), request.getRequestURI());
    }

    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    public ResponseEntity<GenericStandardException> EmailAlreadyRegistered(EmailAlreadyRegisteredException e, HttpServletRequest request) {
        return genericHandlingException(e, HttpStatus.CONFLICT.value(), request.getRequestURI());
    }

    @ExceptionHandler(UsernameAlreadyRegisteredException.class)
    public ResponseEntity<GenericStandardException> UsernameAlreadyRegistered(UsernameAlreadyRegisteredException e, HttpServletRequest request) {
        return genericHandlingException(e, HttpStatus.CONFLICT.value(), request.getRequestURI());
    }

    @ExceptionHandler(PasswordsIsNotEqualsException.class)
    public ResponseEntity<GenericStandardException> passwordsIsNotEquals(PasswordsIsNotEqualsException e, HttpServletRequest request) {
        return genericHandlingException(e, HttpStatus.UNPROCESSABLE_ENTITY.value(), request.getRequestURI());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<GenericStandardException> badCredentials(BadCredentialsException e, HttpServletRequest request) {
        return genericHandlingException(e, HttpStatus.NOT_FOUND.value(), request.getRequestURI());
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<GenericStandardException> UsernameNotFound(UsernameNotFoundException e, HttpServletRequest request) {
        return genericHandlingException(e, HttpStatus.NOT_FOUND.value(), request.getRequestURI());
    }

   /* @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericStandardException> genericException(Exception e, HttpServletRequest request) {
        return genericHandlingException(e, HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI());
    }*/


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> methodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {
        ValidationErrorException error = new ValidationErrorException(
                HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation error occurred.", request.getRequestURI());

        e.getBindingResult().getFieldErrors()
                .forEach(fieldError -> error.addError(new FieldMessage(fieldError.getField(), fieldError.getDefaultMessage())));

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }


    private ResponseEntity<GenericStandardException> genericHandlingException(Throwable e, Integer status, String path) {
        return ResponseEntity.status(status).body(new GenericStandardException(status, e.getMessage(), path));
    }

}