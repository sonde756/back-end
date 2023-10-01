package com.br.techroom.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe de resposta de registro de usuário.
 *
 *@author Edson Rafael
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponseDTO {
    private String username;
    private String email;
}
