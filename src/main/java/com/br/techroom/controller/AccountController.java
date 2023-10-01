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

@RestController
@RequestMapping("/api/v1")
public class AccountController {

    @Autowired
    private AccountServiceImpl accountService;

    @GetMapping("/login")
    public ResponseEntity<LoginResponseDTO> attemptAuthentication(@RequestBody LoginRequestDTO loginRequestDTO){
        return  ResponseEntity.ok(this.accountService.attemptAuthentication(loginRequestDTO));
    }


}
