package com.br.techroom.service.business.rules.Account.register.validations;

import com.br.techroom.service.business.rules.Account.register.RegisterAccountArgs;
import com.br.techroom.service.business.rules.Account.register.RegisterAccountValidation;
import com.br.techroom.service.exception.UsernameAlreadyRegisteredException;
import org.springframework.stereotype.Component;

@Component
public class UsernameExistsValidation implements RegisterAccountValidation {

    @Override
    public void verification(RegisterAccountArgs args) {
        if (args.getRepository().existsByUsername(args.getDto().getUsername())) {
            throw new UsernameAlreadyRegisteredException();
        }
    }
}