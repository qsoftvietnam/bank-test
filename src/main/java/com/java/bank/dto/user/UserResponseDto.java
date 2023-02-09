package com.java.bank.dto.user;

import com.java.bank.type.RoleConstants;
import lombok.Data;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
public class UserResponseDto {
    private String username;
    private String email;
    private String phoneNumber;
    private String address;
    private Set<RoleConstants> roles;
}
