package com.br.techroom.mappers;

import com.br.techroom.builders.entities.AccountBuilder;
import com.br.techroom.dto.request.AccountRegisterRequestDTO;
import com.br.techroom.entities.Account;
import com.br.techroom.enums.RoleName;
import com.br.techroom.service.RoleService;
import com.br.techroom.utils.ConvertingType;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountMapper {

    public static Account requestDtoToEntity(AccountRegisterRequestDTO dto) {
        return AccountBuilder.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .enabled(false)
                .build();
    }
}