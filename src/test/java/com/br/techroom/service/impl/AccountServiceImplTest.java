package com.br.techroom.service.impl;

import com.br.techroom.dto.requests.LoginRequestDTO;
import com.br.techroom.dto.requests.RegisterRequestDTO;
import com.br.techroom.dto.responses.LoginResponseDTO;
import com.br.techroom.exception.ValidationRegisterException;
import com.br.techroom.model.AccountModel;
import com.br.techroom.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

    private LoginRequestDTO loginRequestDTO;
    private Authentication authentication;
    private String token;
    private Map<String, Object> claims;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private AuthenticationManager auhtenticationManager;

    @Mock
    private JwtService jwtService;


    @Test
    public void save() {
        var account = new RegisterRequestDTO("username", "email", "password", "password");
        var accountModel = new AccountModel(null, "username", "email", "password", new Date());

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
        var accountModel = new AccountModel(null, "username", "email", "password", new Date());

        when(accountRepository.existsByUsername(anyString())).thenReturn(false);
        when(accountRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("password");
        when(accountRepository.save(accountModel)).thenThrow(RuntimeException.class);
        when(modelMapper.map(account, AccountModel.class)).thenReturn(accountModel);

        assertThrows(RuntimeException.class, () -> accountService.save(account));
    }

    @BeforeEach
    void setUp() {
        loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUsername("techroom");
        loginRequestDTO.setPassword("techroom");
        authentication = new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
        claims = new HashMap<>();
        claims.put("username", authentication.getName());
        token =
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
    }


    @Test
    void attemptAuthenticationWhenUsernameIsValid() {

        when(auhtenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtService.generateToken(claims)).thenReturn(token);
        LoginResponseDTO loginResponseDto = accountService.attemptAuthentication(loginRequestDTO);
        assertEquals(loginResponseDto.getUsername(), loginRequestDTO.getUsername());
        assertEquals(loginResponseDto.getToken(), token);

    }

}