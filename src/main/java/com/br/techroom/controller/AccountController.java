package com.br.techroom.controller;


import com.br.techroom.dto.request.AccountLoginRequestDTO;
import com.br.techroom.dto.request.AccountRegisterRequestDTO;
import com.br.techroom.dto.response.AccountLoginResponseDTO;
import com.br.techroom.dto.response.AccountRegisterResponseDTO;
import com.br.techroom.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.UnsupportedEncodingException;

@Tag(name = "Account")
@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AccountController {

    @Autowired
    private AccountService service;

    /**
     * Endpoint para registro de um novo usuário.
     *
     * @param dto De request para o resgitro
     * @return Um ResponseEntity contendo o DTO resposta de registro e um código de status HTTP:
     * - 201 Se a conta for registrada com sucesso
     */
    @Operation(summary = "Account registration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account successfully registered")})
    @PostMapping(
            path = "/register",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<AccountRegisterResponseDTO> register(
            @RequestBody @Valid AccountRegisterRequestDTO dto) throws MessagingException, UnsupportedEncodingException {

        AccountRegisterResponseDTO dtoSave = service.register(dto);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dtoSave.getId()).toUri()).body(dtoSave);
    }

    /**
     * Endpoint para autenticação de usuários
     *
     * @param loginRequestDTO the username and the password
     * @return response entity of LoginResponseDto with its username and token
     */
    @Operation(summary = "Account login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account successfully logged in")})
    @PostMapping(
            path = "/login",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AccountLoginResponseDTO> login(@Valid @RequestBody AccountLoginRequestDTO loginRequestDTO) {
        String rawPassword = loginRequestDTO.getPassword();
        return ResponseEntity.ok().body(service.login(loginRequestDTO));
    }

    @Operation(summary = "Confirm email with token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email confirmed"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @GetMapping("/activate/{token}")
    public ResponseEntity<Void> confirmEmail(@PathVariable String token) {
        service.confirmEmail(token);
        return ResponseEntity.ok().build();
    }
}