package com.br.techroom.controller;


import com.br.techroom.dto.responses.ApiResponseConfirmEmail;
import com.br.techroom.service.AccountTokenConfirmEmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "AccountTokenConfirmEmail", tags = {"AccountTokenConfirmEmail"})
@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AccountTokenConfirmEmailController {

    final AccountTokenConfirmEmailService accountTokenConfirmEmailService;

    @Autowired
    public AccountTokenConfirmEmailController(AccountTokenConfirmEmailService accountTokenConfirmEmailService) {
        this.accountTokenConfirmEmailService = accountTokenConfirmEmailService;
    }

    @ApiOperation(value = "Confirm email with token")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Email confirmed"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @GetMapping("/activate/{token}")
    public ResponseEntity<ApiResponseConfirmEmail> confirmEmail(@PathVariable String token) {
        accountTokenConfirmEmailService.confirmEmailWithToken(token);
        return ResponseEntity.ok(new ApiResponseConfirmEmail(true, "Email confirmed"));
    }

}
