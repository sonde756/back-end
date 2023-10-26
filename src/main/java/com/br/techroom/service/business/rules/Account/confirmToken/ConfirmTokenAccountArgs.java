package com.br.techroom.service.business.rules.Account.confirmToken;

import com.br.techroom.entities.Account;
import com.br.techroom.repository.AccountRepository;

public record ConfirmTokenAccountArgs(String token, AccountRepository repository) {
}