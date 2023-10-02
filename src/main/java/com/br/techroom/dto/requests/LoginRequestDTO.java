package com.br.techroom.dto.requests;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginRequestDTO {

    @NotNull(message="Must inform the username or email")
    private String username;
    @NotNull(message="Must inform the password")
    private String password;

}
