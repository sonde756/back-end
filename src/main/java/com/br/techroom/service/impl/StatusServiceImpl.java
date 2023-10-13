package com.br.techroom.service.impl;

import com.br.techroom.exception.ValidationRegisterException;
import com.br.techroom.model.Status;
import com.br.techroom.repository.StatusRepository;
import com.br.techroom.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Author Edson
 */
@Service
public class StatusServiceImpl implements StatusService {

    private final StatusRepository statusRepository;

    @Autowired
    public StatusServiceImpl(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    /**
     * responsible method for finding status by type and status
     *
     * @param typeStatus the type of status
     * @param status     the status
     * @return the status found
     */
    public Status findByStatus(String typeStatus, String status) {
        return statusRepository.findByTypeStatusAndStatus(typeStatus, status)
                .orElseThrow(() -> new ValidationRegisterException("Status não encontrado"));
    }
}
