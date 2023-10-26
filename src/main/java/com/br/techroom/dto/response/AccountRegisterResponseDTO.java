package com.br.techroom.dto.response;


import com.br.techroom.entities.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO para resposta do registro de usuário.
 */

public class AccountRegisterResponseDTO {

    @JsonIgnore
    private Long id;

    private String email;
    private List<Role> roles = new ArrayList<>();


    private AccountRegisterResponseDTO() {
    }

    public AccountRegisterResponseDTO(Long id, String email, VerificationCodeResponseDTO codeDTO) {
        this.id = id;
        this.email = email;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}