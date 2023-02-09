package com.java.bank.dto.user;

import lombok.Data;

@Data
public class SearchUserDto {
    private Integer page = 1;
    private Integer pageSize = 10;
}
