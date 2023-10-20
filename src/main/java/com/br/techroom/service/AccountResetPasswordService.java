package com.br.techroom.service;


import com.br.techroom.dto.requests.ResetPasswordDTO;

public interface AccountResetPasswordService {
    void resetPassword(String email);

    void changePassword(String token, ResetPasswordDTO password);
}
