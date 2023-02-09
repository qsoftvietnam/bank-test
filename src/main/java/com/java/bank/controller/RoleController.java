package com.java.bank.controller;

import com.java.bank.dto.role.CreateRoleDto;
import com.java.bank.service.RoleService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/role")
public class RoleController {
    private @NonNull RoleService roleService;

    @PostMapping("")
    public ResponseEntity<?> createRole(@RequestBody CreateRoleDto request) {
        return ResponseEntity.ok(roleService.createRole(request.getRoleName()));
    }
}
