package com.br.techroom.controller;


import com.br.techroom.dto.requests.RegisterRequestDTO;
import com.br.techroom.dto.responses.RegisterResponseDTO;
import com.br.techroom.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.Value;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

/**
 * Classe responsável por controlar as requisições de registro de usuário.
 *
 * @Author Edson
 */

@Api(value = "Account", tags = { "Account" })
@RestController
@RequestMapping("/api/v1")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ModelMapper modelMapper;


    /**
     * Endpoint registra no banco de dados um novo usuário.
     *
     * @return ResponseEntity<RegisterResponseDTO>
     */
    @ApiOperation(value = "Account registration")
    @ApiResponses(@ApiResponse(code = 201, message = "Account successfully registered"))
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody @Valid RegisterRequestDTO account, Error error) {
        var dados = accountService.save(account);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dados.getIdUser())
                .toUri();

        var dto = modelMapper.map(dados, RegisterResponseDTO.class);
        return ResponseEntity.created(location).body(dto);
    }
}

