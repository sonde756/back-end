package com.br.techroom.service;

import com.br.techroom.exception.ValidationRegisterException;
import com.br.techroom.model.Status;
import com.br.techroom.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public interface StatusService {

    Status findByStatus(String typeStatus, String status);
}

