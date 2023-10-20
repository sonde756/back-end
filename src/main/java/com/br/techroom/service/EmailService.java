package com.br.techroom.service;


public interface EmailService {


    void sendEmailConfirmation(String username, String email, String token);

    void sendEmailPasswordReset(String username, String email, String tokenConfirmation);

    void sendEmailPasswordResetSucess(String email);

    String modelEmailHTML(String username, String token);

    String modelEmailResetPasswordHTML(String username, String token);

    String modelEmailResetPasswordSucessHTML();
}
