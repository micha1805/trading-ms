package com.trading.app.demo.controller;

import com.trading.app.demo.dtos.AuthenticationResponseDTO;
import com.trading.app.demo.dtos.LoginRequestDTO;
import com.trading.app.demo.dtos.SignupRequestDTO;
import com.trading.app.demo.service.AuthenticationService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> login(
            @RequestParam @NotBlank(message = "Email is required") @Email(message = "Invalid email format") String email,
            @RequestParam @NotBlank(message = "Password is required") String password) {
        return ResponseEntity.ok(authenticationService.login(email, password));
    }

    @PostMapping(path="/signup")
    public ResponseEntity<AuthenticationResponseDTO> signup(@RequestBody SignupRequestDTO signupRequest){
        return ResponseEntity.ok(authenticationService.signup(signupRequest));
    }
}