package com.br.techroom.repository;

import com.br.techroom.model.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountModel, Long>{

    boolean existsByUsername(String getUsername);

    boolean existsByEmail(String getEmail);
}
