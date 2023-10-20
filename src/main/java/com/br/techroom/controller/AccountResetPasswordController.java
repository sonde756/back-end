package com.br.techroom.controller;


import com.br.techroom.dto.requests.ResetPasswordDTO;
import com.br.techroom.dto.responses.ApiResponseOk;
import com.br.techroom.service.AccountResetPasswordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "AccountResetPassword", tags = {"AccountResetPassword"})
@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AccountResetPasswordController {

    final
    AccountResetPasswordService accountResetPasswordService;

    @Autowired
    public AccountResetPasswordController(AccountResetPasswordService accountResetPasswordService) {
        this.accountResetPasswordService = accountResetPasswordService;
    }

    @ApiOperation(value = "Reset password")
    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponseOk> resetPasswordEmail(String email) {
        accountResetPasswordService.resetPassword(email);
        return ResponseEntity.ok(new ApiResponseOk(true, "Email sent"));
    }


    @ApiOperation(value = "Change password")
    @PostMapping("/reset-password/{token}")
    public ResponseEntity<ApiResponseOk> resetPassword(@PathVariable String token, @RequestBody ResetPasswordDTO password) {
        accountResetPasswordService.changePassword(token, password);
        return ResponseEntity.ok(new ApiResponseOk(true, "Password changed"));
    }
}