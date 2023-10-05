package com.br.techroom.service;

import com.br.techroom.dto.requests.LoginRequestDTO;
import com.br.techroom.dto.requests.RegisterRequestDTO;
import com.br.techroom.dto.responses.LoginResponseDTO;
import com.br.techroom.model.AccountModel;

public interface AccountService {
    AccountModel save(RegisterRequestDTO account);

    LoginResponseDTO attemptAuthentication(LoginRequestDTO loginRequestDto);
}