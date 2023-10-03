package com.br.techroom.controller;

import com.br.techroom.dto.requests.LoginRequestDTO;
import com.br.techroom.dto.responses.LoginResponseDTO;
import com.br.techroom.service.impl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class AccountController {

    @Autowired
    private AccountServiceImpl accountService;

    /**
     * enpoit point to try to authenticate an account
     * @param loginRequestDTO the username and the password
     * @return response entity of LoginResponseDto with its username and token
     */
    @GetMapping("/login")
    public ResponseEntity<LoginResponseDTO> attemptAuthentication(@Valid @RequestBody LoginRequestDTO loginRequestDTO){
        return  ResponseEntity.ok(this.accountService.attemptAuthentication(loginRequestDTO));
    }


}
