package com.br.techroom.repository;

import com.br.techroom.model.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;

public class AccountRepository implements JpaRepository<Long, AccountModel> {
}
