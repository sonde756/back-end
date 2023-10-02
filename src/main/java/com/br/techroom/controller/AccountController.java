package com.br.techroom.controller;


import com.br.techroom.dto.requests.RegisterRequestDTO;
import com.br.techroom.dto.responses.ApiResponseRegister;
import com.br.techroom.dto.responses.RegisterResponseDTO;
import com.br.techroom.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
@CrossOrigin(origins = "*", maxAge = 3600)
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
    public ResponseEntity<ApiResponseRegister<RegisterResponseDTO>> register(@RequestBody @Valid RegisterRequestDTO account) {
        var dados = accountService.save(account);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dados.getIdUser())
                .toUri();
        var dto = modelMapper.map(dados, RegisterResponseDTO.class);
        var response = new ApiResponseRegister<>("success", dto);
        return ResponseEntity.created(location).body(response);
    }

}

