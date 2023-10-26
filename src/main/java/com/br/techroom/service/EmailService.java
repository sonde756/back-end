package com.br.techroom.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.support.register}")
    private String supportMail;

    /**
     * Responsabilidade do método de enviar email para um destinatário
     *
     * @param subject Armazena o assunto do email
     * @param email Destinatário de envio
     * @param content Conteúdo do email
     */
    public void sendEmailConfirmation(String subject, String email, String content) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setTo(email);
        helper.setFrom(supportMail,"TechRoom");
        helper.setSubject(subject);
        helper.setText(content, true);

        javaMailSender.send(message);
    }

    /**
     * Responsabilidade do método retornar um modelo da estrutura HTML
     *
     * @param username O nome do usuário
     * @param token O token de ativação da conta
     */

    public String assembleHTMLEmailStructureForToken(String username, String token) {
        String baseUrl = "http://localhost:8081";
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