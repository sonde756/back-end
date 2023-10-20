package com.br.techroom.repository;


import com.br.techroom.model.AccountTokenPasswordReset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTokenPasswordResetRepository extends JpaRepository<AccountTokenPasswordReset,
        Long> {


}
