package com.trading.app.demo.service;

import com.trading.app.demo.dtos.LoginRequestDTO;
import com.trading.app.demo.dtos.SignupRequestDTO;
import com.trading.app.demo.dtos.AuthenticationResponseDTO;
import com.trading.app.demo.model.Profile;
import com.trading.app.demo.model.Role;
import com.trading.app.demo.model.User;
import com.trading.app.demo.repository.ProfileRepository;
import com.trading.app.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponseDTO signup(SignupRequestDTO request) {
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);

        Profile profile = Profile.builder()
                .firstName(request.getFirst_name())
                .lastName(request.getLast_name())
                .address(request.getAddress())
                .phoneNumber(request.getPhone_number())
                .build();

        profileRepository.save(profile);
        profile.setUser(user);
        profileRepository.save(profile);


        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponseDTO.builder()
                .token(jwtToken )
                .build();
    }

    public AuthenticationResponseDTO login(LoginRequestDTO request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponseDTO.builder()
                .token(jwtToken )
                .build();
    }
}