package com.br.techroom.util;

import java.security.SecureRandom;

public class TokenGenerator {

    public static String generateToken() {
        SecureRandom random = new SecureRandom();
        byte[] tokenBytes = new byte[60 / 2];
        random.nextBytes(tokenBytes);
        StringBuilder token = new StringBuilder(60);

        for (byte b : tokenBytes) {
            token.append(String.format("%02x", b));
        }
        return token.toString();
    }

}
