package com.br.techroom.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


/**
 * Classe de requisição de registro de usuário.
 *
 *@author Edson Rafael
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {
    @NotBlank
    @Size(max = 20, message = "Excedido limite de 20 caracteres")
    private String username;

    @NotBlank
    @Email
    @Size(max = 100, message = "Excedido limite de 100 caracteres")
    private String email;

    @Size(min = 6, max = 20, message = "Senha deve ter entre 6 e 20 caracteres")
    private String password;

    @NotBlank
    private String confirmPassword;
}
