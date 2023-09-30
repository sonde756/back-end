package com.br.techroom.controller;

import com.br.techroom.dto.responses.LoginResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AccountController {


    @GetMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(){

        return ResponseEntity.ok().build();
    }


}
