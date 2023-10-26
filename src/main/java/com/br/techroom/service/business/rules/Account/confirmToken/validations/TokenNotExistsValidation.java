package com.br.techroom.service.business.rules.Account.confirmToken.validations;

import com.br.techroom.service.business.rules.Account.confirmToken.ConfirmTokenAccountArgs;
import com.br.techroom.service.business.rules.Account.confirmToken.ConfirmTokenAccountValidation;
import com.br.techroom.service.exception.TokenNotExistsException;
import org.springframework.stereotype.Component;

@Component
public class TokenNotExistsValidation implements ConfirmTokenAccountValidation {

    @Override
    public void verification(ConfirmTokenAccountArgs args) {
        if (args.repository().findByToken(args.token()) == null) {
            throw new TokenNotExistsException();
        }
    }
}