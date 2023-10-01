package com.br.techroom.service;

import com.br.techroom.dto.requests.RegisterRequestDTO;
import com.br.techroom.model.AccountModel;

public interface AccountService {

    AccountModel save(RegisterRequestDTO account);
}