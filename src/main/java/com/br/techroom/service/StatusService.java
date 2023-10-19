package com.br.techroom.service;

import com.br.techroom.model.Status;


public interface StatusService {

    Status findByStatus(String typeStatus, String status);
}

