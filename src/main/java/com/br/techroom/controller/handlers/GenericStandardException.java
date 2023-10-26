package com.br.techroom.controller.handlers;

import java.time.Instant;


public class GenericStandardException {

    private Instant timestamp = Instant.now();
    private Integer status;
    private String error;
    private String path;

    public GenericStandardException(Integer status, String error, String path) {
        this.status = status;
        this.error = error;
        this.path = path;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}