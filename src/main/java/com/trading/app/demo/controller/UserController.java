package com.trading.app.demo.controller;

import com.trading.app.demo.dtos.UserUpdateRequestDTO;
import com.trading.app.demo.dtos.CurrentBalanceResponseDTO;
import com.trading.app.demo.model.User;
import com.trading.app.demo.service.ProfileService;
import com.trading.app.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/user")
public class UserController {

    private final UserService userService;
    private final ProfileService profileService;
    private final PasswordEncoder passwordEncoder;

    // @Autowired annotation is a Spring tool to deal with Dependency Injection :
    // the studentService instance will automatically be injected into the constructor
    // BUT studentService MUST BE A JAVA BEAN, as so we must annotate studentservice with @Component for that to work
    // As our StudentService class is also a service we will instead annotate it with @Service for more readability

    @GetMapping
    public List<User> getUsers(){
        return userService.getUsers();

    }
    @GetMapping("/{userId}")
    public User getUserById(@PathVariable("userId") Long userId){
        return userService.getUserById(userId);
    }

    @PutMapping
    public void updateUser(@RequestBody UserUpdateRequestDTO request, @RequestHeader("Authorization") String authHeader) {

        // TODO
        //  - if no user found
        //  - if no profile found
        //  - if body's request is partial information (for now it is the full data)

        User user = userService.getUserFromHeader(authHeader);

        profileService.updateFullProfile(user.getId(), request);

        // TODO update user MUST BE OPTIMISED !!!!!
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userService.update(user);

    }

    @GetMapping(path = "/currentBalance")
    public ResponseEntity<CurrentBalanceResponseDTO> currentBalance(@RequestHeader("Authorization") String authHeader) {
        User user = userService.getUserFromHeaderWithTradesAndWires(authHeader);
        CurrentBalanceResponseDTO response = CurrentBalanceResponseDTO.builder()
                .currentBalanceInCent(userService.getCurrentBalance(user))
                .build();
        return ResponseEntity.ok(response);
    }

}





