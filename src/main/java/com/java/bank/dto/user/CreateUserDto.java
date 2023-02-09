package com.java.bank.dto.user;

import com.java.bank.entity.Role;
import lombok.Data;

@Data
public class CreateUserDto {
    private String username;
    private String email;
    private String phoneNumber;
    private String address;
}
