package com.br.techroom.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "account_token_email")
public class AccountTokenConfirmEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user_token")
    private Long idUserToken;

    @Column(name = "token_confirmation", length = 60, nullable = false)
    private String tokenConfirmation;

    @Column(name = "date_expiration",
            nullable = false, columnDefinition = "TIMESTAMP", length = 35)
    private LocalDateTime dateExpiration;

    @OneToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    private AccountModel idUser;

}
