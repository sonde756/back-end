package com.br.techroom.service.business.rules.Account.confirmToken.validations;

import com.br.techroom.entities.Account;
import com.br.techroom.service.AccountService;
import com.br.techroom.service.EmailService;
import com.br.techroom.service.business.rules.Account.confirmToken.ConfirmTokenAccountArgs;
import com.br.techroom.service.business.rules.Account.confirmToken.ConfirmTokenAccountValidation;
import com.br.techroom.service.exception.TokenHasExpiredException;
import com.br.techroom.service.exception.TokenNotExistsException;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

@Component
public class TokenExpiredValidation implements ConfirmTokenAccountValidation {

    @Autowired
    private EmailService emailService;

    @Override
    public void verification(ConfirmTokenAccountArgs args) {

        Account entity = args.repository().findByToken(args.token());

        if (entity != null) {
            if (entity.getToken().getDateExpiration().isBefore(LocalDateTime.now())) {
                entity.setToken(AccountService.generateCode());
                args.repository().save(entity);

                try {
                    emailService.sendEmailConfirmation(entity.getToken().getToken(), entity.getEmail(),
                            emailService.assembleHTMLEmailStructureForToken(entity.getUsername(), entity.getToken().getToken()));
                } catch (MessagingException | UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
                throw new TokenHasExpiredException();
            }
        } else {
            throw new TokenNotExistsException();
        }
    }
}