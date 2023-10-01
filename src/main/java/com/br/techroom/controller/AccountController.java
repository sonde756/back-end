package com.br.techroom.controller;


import com.br.techroom.dto.requests.RegisterRequestDTO;
import com.br.techroom.dto.responses.RegisterResponseDTO;
import com.br.techroom.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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


    /**
     * Endpoint registra no banco de dados um novo usuário.
     *
     * @return ResponseEntity<RegisterResponseDTO>
     */
    @ApiOperation(value = "Account registration")
    @ApiResponses(@ApiResponse(code = 201, message = "Account successfully registered"))
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@Valid @RequestBody RegisterRequestDTO account) {
        var dados = accountService.save(account);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dados.getIdUser())
                .toUri();

        var dto = new RegisterResponseDTO(dados.getUsername(), dados.getEmail());
        return ResponseEntity.created(location).body(dto);
    }
}

