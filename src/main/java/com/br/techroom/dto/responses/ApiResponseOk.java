package com.br.techroom.dto.responses;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe de resposta de registro de usuário.
 *
 *@Author Edson Rafael
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseOk {
    private Boolean success;
    private String message;
}
