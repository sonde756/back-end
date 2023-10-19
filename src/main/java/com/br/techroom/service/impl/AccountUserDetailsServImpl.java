package com.br.techroom.service.impl;

import com.br.techroom.constants.StatusConstants;
import com.br.techroom.exception.ValidationLoginException;
import com.br.techroom.model.AccountModel;
import com.br.techroom.repository.AccountRepository;
import com.br.techroom.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * class implementation of the UserDetailsService for the AccountModel.
 *
 * @Author Victor Vilar
 */
@Service
public class AccountUserDetailsServImpl implements UserDetailsService {

    /**
     * patten to find if the given username it is an email
     */
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private final AccountRepository accountRepository;
    private final StatusService statusService;


    @Autowired
    public AccountUserDetailsServImpl(AccountRepository accountRepository, StatusService statusService) {
        this.accountRepository = accountRepository;
        this.statusService = statusService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<AccountModel> account;

        //if the username pattern is true to email it will search the account model by find by email;
        // else will try to find the user by it username
        if (validate(username)) {
            account = this.accountRepository.findByEmail(username);
        } else {
            account = this.accountRepository.findByUsername(username);
        }

        if (account.isEmpty()) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        if (account.get().getStatus().equals(statusService.findByStatus(StatusConstants.TYPE_USER, StatusConstants.USER_NEW))) {
            throw new ValidationLoginException("Usuário com email não confirmado");
        }

        //if the user was not found, then it will trow usernameNotFoundException

        //return the user
        return account.get();

    }

    /**
     * validates the username send by the user
     *
     * @param emailStr the
     * @return match
     */
    private boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.matches();
    }

}
