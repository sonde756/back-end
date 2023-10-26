package com.br.techroom.service.business.rules.Account.register.validations;

import com.br.techroom.service.business.rules.Account.register.RegisterAccountArgs;
import com.br.techroom.service.business.rules.Account.register.RegisterAccountValidation;
import com.br.techroom.service.exception.EmailAlreadyRegisteredException;
import org.springframework.stereotype.Component;

@Component
public class EmailExistsValidation implements RegisterAccountValidation {

    @Override
    public void verification(RegisterAccountArgs args) {
        if (args.getRepository().existsByEmail(args.getDto().getEmail())) {
            throw new EmailAlreadyRegisteredException();
        }
    }
}