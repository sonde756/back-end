package com.br.techroom.service.business.rules.Account.register.validations;

import com.br.techroom.service.business.rules.Account.register.RegisterAccountArgs;
import com.br.techroom.service.business.rules.Account.register.RegisterAccountValidation;
import com.br.techroom.service.exception.PasswordsIsNotEqualsException;
import org.springframework.stereotype.Component;

@Component
public class PasswordNotConfirm implements RegisterAccountValidation {

    @Override
    public void verification(RegisterAccountArgs args) {
        if (!args.getDto().getPassword().equals(args.getDto().getConfirmPassword())) {
            throw new PasswordsIsNotEqualsException();
        }
    }
}