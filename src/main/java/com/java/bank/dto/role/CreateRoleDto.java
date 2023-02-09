package com.java.bank.dto.role;

import com.java.bank.type.RoleConstants;
import lombok.Data;

@Data
public class CreateRoleDto {
    private RoleConstants roleName;
}
