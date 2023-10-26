package com.br.techroom.service.business.rules.Account.confirmToken.validations;

import com.br.techroom.service.business.rules.Account.confirmToken.ConfirmTokenAccountArgs;
import com.br.techroom.service.business.rules.Account.confirmToken.ConfirmTokenAccountValidation;
import com.br.techroom.service.exception.AccountAlreadyActivatedException;

public class AccountAlreadyActivated implements ConfirmTokenAccountValidation {

    @Override
    public void verification(ConfirmTokenAccountArgs args) {
        if(args.repository().findByToken(args.token()).isEnabled()){
            throw new AccountAlreadyActivatedException();
        }
    }
}