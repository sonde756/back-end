package com.br.techroom.service;

import com.br.techroom.model.AccountModel;

public interface AccountTokenConfirmEmailService {
    void save(AccountModel account);
    void confirmEmailWithToken(String token);

}
