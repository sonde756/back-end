package com.br.techroom.repository;

import com.br.techroom.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
    Optional<Status> findByTypeStatusAndStatus(String typeStatus, String status);
}
