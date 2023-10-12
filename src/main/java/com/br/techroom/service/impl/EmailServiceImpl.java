package com.br.techroom.service.impl;


import com.br.techroom.exception.InternalErrorException;
import com.br.techroom.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @Author Edson
 */
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    /**
     * responsible method for sending email confirmation
     *
     * @param username the username of the user
     * @param email    the email of the user
     * @param token    the token of the user
     */
    @Override
    public void sendEmailConfirmation(String username, String email, String token) throws InternalErrorException {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("edsonrafael756l@gmail.com");
            helper.setTo(email);
            helper.setSubject("Confirmação de cadastro");

            String content = modelEmailHTML(username, token);
            helper.setText(content, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new InternalErrorException("Erro ao enviar o e-mail de confirmação.");
        }
    }


    /**
     * responsible method for email confirmation model
     *
     * @param username the username of the user
     * @param token    the token of the user
     */
    @Override
    public String modelEmailHTML(String username, String token) {
        String baseUrl = System.getenv("BASE_URL");

        return "<!DOCTYPE html>\n" +
               "<html lang=\"pt-br\">\n" +
               "<head>\n" +
               "    <meta charset=\"UTF-8\">\n" +
               "    <title><html lang=\"en\">\n" +
               "        <head>\n" +
               "        <meta charset=\"UTF-8\">\n" +
               "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
               "        <title>Confirmação de Cadastro</title>\n" +
               "    <style>\n" +
               "\n" +
               "        body {\n" +
               "            font-family: Poppins, sans-serif;\n" +
               "            text-align: center;\n" +
               "        }\n" +
               "\n" +
               "        .container {\n" +
               "            margin: 50px auto;\n" +
               "            max-width: 600px;\n" +
               "            padding: 20px;\n" +
               "            background-color: #f9f9f9;\n" +
               "            border-radius: 10px;\n" +
               "        }\n" +
               "\n" +
               "        .button {\n" +
               "            display: inline-block;\n" +
               "            font-size: 16px;\n" +
               "            padding: 10px 20px;\n" +
               "            text-decoration: white;\n" +
               "            background-color: #007BFF;\n" +
               "            color: #fff;\n" +
               "            border-radius: 5px;\n" +
               "        }\n" +
               "\n" +
               "    </style>\n" +
               "</head>\n" +
               "<body>\n" +
               "<div class=\"container\">\n" +
               "    <h1>Confirmação de Cadastro</h1>\n" +
               "    <p>Olá, seja bem-vindo " + username + "!</p>\n" +
               "    <p>Por favor, clique no botão abaixo para confirmar o seu cadastro:</p>\n" +
               "    <a class=\"button\" id=\"bn\" style=\"color: #fff; \" href=\"" + baseUrl + "/api/v1/activate/" + token + "\">Confirmar Cadastro</a>\n" +
               "</div>\n" +
               "</body>\n" +
               "</html>";
    }

}
