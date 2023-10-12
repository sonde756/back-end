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

    @Autowired
    AccountTokenConfirmEmailService accountTokenConfirmEmailService;

    @ApiOperation(value = "Confirm email with token")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Email confirmed"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Token not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping("/activate/{token}")
    public ResponseEntity<ApiResponseConfirmEmail> confirmEmail(@PathVariable String token) {
        accountTokenConfirmEmailService.confirmEmailWithToken(token);
        return ResponseEntity.ok(new ApiResponseConfirmEmail(true, "Email confirmed"));
    }

}
