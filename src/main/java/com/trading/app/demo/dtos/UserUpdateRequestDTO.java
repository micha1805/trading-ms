package com.trading.app.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateRequestDTO {
    private String email;
    private String password;
    private String first_name;
    private String last_name;
    private String address;
    private String phone;
}
