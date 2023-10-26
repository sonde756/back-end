package com.br.techroom.service;

import com.br.techroom.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AccountUserDetailsService implements UserDetailsService {

    private final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDetails account;

        if (validate(username)) {
            account = accountRepository.findByEmail(username);
        } else {
            account = accountRepository.findByUsername(username);
        }

        if (account == null) {
            throw new UsernameNotFoundException("Email ou senha incorretos");
        }

        if (!account.isEnabled()) {
            throw new BadCredentialsException("Usuário com email não verificado");
        }

        return account;
    }

    /**
     * Valida o formato da String (Email/Username)
     *
     * @param emailStr the
     * @return Um valor boolean:
     * - true Se o formato ser de um email válido
     * - false Se o formato for de um username
     */
    private boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.matches();
    }

}