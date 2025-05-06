package com.trading.app.demo.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {
    private String email;
    private String password;

    public LoginRequestDTO(@NotBlank(message = "Email is required") @Email(message = "Invalid email format") String email, @NotBlank(message = "Password is required") String password) {
    }
}
