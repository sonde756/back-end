package com.br.techroom.service;

import com.br.techroom.entities.Role;
import com.br.techroom.enums.RoleName;
import com.br.techroom.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repository;

    public Role findById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public List<Role> separateRolesWithHierarchy(RoleName role) {
        List<Role> listCreated = new ArrayList<>();

        Role admin = this.findById(1L);
        Role moderator = this.findById(2L);
        Role user = this.findById(3L);

        if (is(role, user.getRoleName())) {
            listCreated.add(this.findById(3L));
        } else if (is(role, admin.getRoleName())) {
            listCreated.addAll(Arrays.asList(admin, user, moderator));
        } else if (is(role, moderator.getRoleName())) {
            listCreated.addAll(Arrays.asList(user, moderator));
        }
        return listCreated;
    }

    public static boolean is(RoleName role, RoleName roleName) {
        return role.equals(roleName);
    }
}