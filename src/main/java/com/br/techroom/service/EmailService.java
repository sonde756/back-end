package com.br.techroom.service;


public interface EmailService {


    void sendEmailConfirmation(String username, String email, String token);

    String modelEmailHTML(String username, String token);
}
