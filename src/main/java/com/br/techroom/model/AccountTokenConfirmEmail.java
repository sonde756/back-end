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

    @Column(name = "token_confirmation")
    private String tokenConfirmation;

    @Column(name = "date_expiration")
    private LocalDateTime dateExpiration;

    @OneToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    private AccountModel idUser;

}
