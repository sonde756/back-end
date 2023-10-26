package com.br.techroom.mappers;

import com.br.techroom.builders.dto.response.VerificationCodeResponseDTOBuilder;
import com.br.techroom.dto.response.VerificationCodeResponseDTO;
import com.br.techroom.entities.VerificationCode;

public class VerificationCodeMapper {

    public static VerificationCodeResponseDTO entityToResponseDTO(VerificationCode entity) {
        return VerificationCodeResponseDTOBuilder.builder()
                .token(entity.getToken())
                .dateExpiration(entity.getDateExpiration())
                .build();
    }
}