package com.br.techroom.handle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessageError {

    @Getter
    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
    public ResponseMessageError(int status, String message, String requestURI) {
        this.timestamp = Instant.now();
        this.status = status;
        this.message = message;
        this.path = requestURI;
    }
}