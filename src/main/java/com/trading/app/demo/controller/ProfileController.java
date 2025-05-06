package com.trading.app.demo.controller;

import com.trading.app.demo.dtos.FullProfileResponseDTO;
import com.trading.app.demo.model.Profile;
import com.trading.app.demo.model.User;
import com.trading.app.demo.service.ProfileService;
import com.trading.app.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<FullProfileResponseDTO> getProfile(@RequestHeader("Authorization") String authHeader) {

        // should take care of the case when no user is found:
        User user = userService.getUserFromHeader(authHeader);
        FullProfileResponseDTO response;
        Profile profile = profileService.findByUserId(user.getId());

        // building the response object:
        response = FullProfileResponseDTO.builder()
                .email(user.getEmail())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .address(profile.getAddress())
                .phoneNumber(profile.getPhoneNumber())
                .build();

        return ResponseEntity.ok(response);
    }

    // A method to test stuff :
    @GetMapping(path = "/test")
    public ResponseEntity<String> test(@RequestBody String requestBody, @RequestHeader("Authorization") String authHeader) {
        // I must find a way to do an equivalent of rails before_action to grab the user
        // before each method that need it. Java filter ?
        User currentUser = userService.getUserFromHeader(authHeader);
        System.out.println(currentUser.toString());
        System.out.println(currentUser.getId());
        return ResponseEntity.ok(authHeader + requestBody);
    }

    // Another way to send json and read request body, less formal doesn't use any response
    // class, just a Map.
    @GetMapping(path = "/test2")
    public ResponseEntity<Map<String, String>> test2(@RequestBody Map<String, Object> transactionMap) {

        // get the value associated to the "hello" key in the request's body :
        String hello = (String) transactionMap.get("hello");

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("myKey", hello);
        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }
}
