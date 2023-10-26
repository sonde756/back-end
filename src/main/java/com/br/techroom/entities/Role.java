package com.br.techroom.entities;

import com.br.techroom.enums.RoleName;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;


@Entity
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    public Role() {
    }

    public Role(Long id, RoleName role) {
        this.id = id;
        this.roleName = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName role) {
        this.roleName = role;
    }

    @Override
    public String getAuthority() {
        return roleName.name();
    }
}