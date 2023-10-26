package com.br.techroom.repository;

import com.br.techroom.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByUsername(String getUsername);

    boolean existsByEmail(String getEmail);

    @Query("SELECT a FROM Account a INNER JOIN a.token t WHERE t.token = :token")
    Account findByToken(String token);

    @Query("SELECT u FROM Account u WHERE UPPER(u.email) LIKE UPPER(CONCAT('%', :email, '%'))")
    UserDetails findByEmail(String email);

    UserDetails findByUsername(String username);
}