package com.br.techroom.service.impl;

import com.br.techroom.dto.requests.LoginRequestDTO;
import com.br.techroom.dto.requests.RegisterRequestDTO;
import com.br.techroom.dto.responses.LoginResponseDTO;
import com.br.techroom.exception.InternalErrorException;
import com.br.techroom.exception.ValidationRegisterException;
import com.br.techroom.model.AccountModel;
import com.br.techroom.repository.AccountRepository;
import com.br.techroom.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Edson Rafael
 */
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final AuthenticationManager auhtenticationManager;
    private final JwtService jwtService;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, AuthenticationManager auhtenticationManager, JwtService jwtService) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.auhtenticationManager = auhtenticationManager;
        this.jwtService = jwtService;
    }

    /**
     * Método responsável por salvar um novo usuário no banco de dados.
     *
     * @return AccountModel
     */
    @Override
    @Transactional
    public AccountModel save(RegisterRequestDTO account) {
        try {

            if (!account.getPassword().equals(account.getConfirmPassword())) {
                throw new ValidationRegisterException("Senhas não conferem");
            }
            if (accountRepository.existsByUsername(account.getUsername())) {
                throw new ValidationRegisterException("Nome de usuário já existe");
            }
            if (accountRepository.existsByEmail(account.getEmail())) {
                throw new ValidationRegisterException("Email já existe");
            }

            account.setPassword(passwordEncoder.encode(account.getPassword()));
            var accountModel = modelMapper.map(account, AccountModel.class);

            accountModel.setCreatedAt(new Date());

            return accountRepository.save(accountModel);
        } catch (DataAccessException e) {
            throw new InternalErrorException(e.getMessage());
        }
    }

    /**
     * The attempt to authentication method
     *
     * @param loginRequestDto username and password from a registred user
     * @return Login responsedto with its usename and token
     */

    public LoginResponseDTO attemptAuthentication(LoginRequestDTO loginRequestDto) {
        try {

            //attempt to authenticate the login request
            Authentication auth = this.auhtenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
            );

            //if there is no error then
            //create a new login response dto
            LoginResponseDTO loginResponseDto = new LoginResponseDTO();

            //setting name
            loginResponseDto.setUsername(auth.getName());

            //creating the claims for the token and return the token
            loginResponseDto.setToken(this.jwtService.generateToken(this.createClaims(auth)));

            return loginResponseDto;
        } catch (DataAccessException e) {
            throw new InternalErrorException(e.getMessage());
        } catch (AuthenticationException e) {
            throw new ValidationRegisterException("Usuário ou senha inválidos");
        }
    }


    /**
     * create claims to the jwt
     *
     * @param authentication authentication
     * @return claims
     */
    private Map<String, Object> createClaims(Authentication authentication) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", authentication.getName());

        return claims;

    }


}
