package com.br.techroom.repository;

import com.br.techroom.model.AccountTokenConfirmEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTokenConfirmEmailRepository extends JpaRepository<AccountTokenConfirmEmail,
        Long> {

    AccountTokenConfirmEmail findByTokenConfirmation(String token);
}
