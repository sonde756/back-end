package com.br.techroom.repository;

import com.br.techroom.model.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountModel, Long>{
    boolean existsByUsername(String getUsername);
    boolean existsByEmail(String getEmail);
    Optional<AccountModel> findByUsername(String username);
    Optional<AccountModel> findByEmail(String email);
}
