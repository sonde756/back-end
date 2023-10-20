package com.br.techroom.dto.requests;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordDTO {

    @Size(min = 6, max = 20, message = "Senha deve ter entre 6 e 20 caracteres")
    private String password;

    @NotBlank(message = "Confirmação de senha não pode ser vazio")
    private String confirmPassword;
}
