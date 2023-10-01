package com.br.techroom.service.impl;

import com.br.techroom.dto.requests.RegisterRequestDTO;
import com.br.techroom.exception.ValidationRegisterException;
import com.br.techroom.model.AccountModel;
import com.br.techroom.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {


    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private ModelMapper modelMapper;


    @Test
    public void save() {
        var account = new RegisterRequestDTO("username", "email", "password", "password");
        var accountModel = new AccountModel(null, "username", "email", "password");

        when(accountRepository.existsByUsername(anyString())).thenReturn(false);
        when(accountRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("password");
        when(accountRepository.save(accountModel)).thenReturn(accountModel);
        when(modelMapper.map(account, AccountModel.class)).thenReturn(accountModel);

        var result = accountService.save(account);

        assertEquals(accountModel, result);
    }

    @Test
    public void saveWithPasswordNotEqualsConfirmPassword() {
        var account = new RegisterRequestDTO("username", "email", "password", "password2");

        assertThrows(ValidationRegisterException.class, () -> accountService.save(account));
    }

    @Test
    public void saveWithUsernameAlreadyExists() {
        var account = new RegisterRequestDTO("username", "email", "password", "password");

        when(accountRepository.existsByUsername(anyString())).thenReturn(true);

        assertThrows(ValidationRegisterException.class, () -> accountService.save(account));
    }

    @Test
    public void saveWithEmailAlreadyExists() {
        var account = new RegisterRequestDTO("username", "email", "password", "password");

        when(accountRepository.existsByUsername(anyString())).thenReturn(false);
        when(accountRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(ValidationRegisterException.class, () -> accountService.save(account));
    }

    @Test
    public void saveWithInternalErrorException() {
        var account = new RegisterRequestDTO("username", "email", "password", "password");
        var accountModel = new AccountModel(null, "username", "email", "password");

        when(accountRepository.existsByUsername(anyString())).thenReturn(false);
        when(accountRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("password");
        when(accountRepository.save(accountModel)).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> accountService.save(account));
    }


}