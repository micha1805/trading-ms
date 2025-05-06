package com.trading.app.demo.controller;

import com.trading.app.demo.dtos.AuthenticationResponseDTO;
import com.trading.app.demo.dtos.LoginRequestDTO;
import com.trading.app.demo.dtos.SignupRequestDTO;
import com.trading.app.demo.service.AuthenticationService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @GetMapping(path="/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }

    @PostMapping(path="/signup")
    public ResponseEntity<AuthenticationResponseDTO> signup(@RequestBody SignupRequestDTO signupRequest){
        return ResponseEntity.ok(authenticationService.signup(signupRequest));
    }
}
