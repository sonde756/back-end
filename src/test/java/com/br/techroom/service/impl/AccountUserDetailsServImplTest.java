package com.br.techroom.service.impl;

import com.br.techroom.model.AccountModel;
import com.br.techroom.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountUserDetailsServImplTest {


    @InjectMocks
    AccountUserDetailsServImpl accountUserDetailsServ;

    @Mock
    AccountRepository accountRepository;

    String username;
    String email;

    final AccountModel accountModel = new AccountModel();

    @BeforeEach
    void setUp(){
        username = "techroom";
        email = "techroom@techroom.com.br";
        accountModel.setUsername("techroom");
        accountModel.setEmail("techroom@techroom.com.br");
        accountModel.setPassword("12345");
    }

    @Test
    void loadUserByUsernameSuccessfullyWhenPassValidUsername() {
        when(this.accountRepository.findByUsername(username)).thenReturn(Optional.of(accountModel));
        UserDetails userDetails = this.accountUserDetailsServ.loadUserByUsername(username);
        assertEquals(userDetails.getUsername(),accountModel.getUsername());
        assertEquals(userDetails.getPassword(),accountModel.getPassword());

    }

    @Test
    void loadUserByUsernameSuccessfullyWhenPassValidEmail() {
        when(this.accountRepository.findByEmail(email)).thenReturn(Optional.of(accountModel));
        UserDetails userDetails = this.accountUserDetailsServ.loadUserByUsername(email);
        assertEquals(userDetails.getUsername(),accountModel.getUsername());
        assertEquals(userDetails.getPassword(),accountModel.getPassword());
    }

    @Test
    void loadUserByUsernameThrowsUsernameNotFoundExceptionWhenPassInvalidUsername() {

        when(this.accountRepository.findByUsername(username)).thenReturn(Optional.empty());
        UsernameNotFoundException thrown = Assertions.assertThrows(UsernameNotFoundException.class,
                () -> this.accountUserDetailsServ.loadUserByUsername(username));

        Assertions.assertEquals("username not found !", thrown.getMessage());


    }

    @Test
    void loadUserByUsernameThrowsUsernameNotFoundExceptionWhenPassInvalidEmail() {

        when(this.accountRepository.findByEmail(email)).thenReturn(Optional.empty());
        UsernameNotFoundException thrown = Assertions.assertThrows(UsernameNotFoundException.class,
                () -> this.accountUserDetailsServ.loadUserByUsername(email));

        Assertions.assertEquals("username not found !", thrown.getMessage());
    }
}