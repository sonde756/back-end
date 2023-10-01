package com.br.techroom.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;

/**
 * Classe de modelo de conta de usuário.
 *
 *@author Edson Rafael
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "idUser")
@Table
@Entity
public class AccountModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idUser;
    @Column(unique = true, nullable = false, length = 20)
    private String username;
    @Column(unique = true, nullable = false, length = 100)
    @Email
    private String email;
    @Column(nullable = false)
    private String password;
}
