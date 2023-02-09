package com.java.bank.service;

import com.java.bank.entity.Role;
import com.java.bank.repository.RoleRepository;
import com.java.bank.type.RoleConstants;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleService {
    private @NonNull RoleRepository roleRepository;

    public Role createRole(RoleConstants roleName) {
        Role newRole = new Role();
        newRole.setRoleName(roleName);
        return roleRepository.save(newRole);
    }
}
