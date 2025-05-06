package com.trading.app.demo.service;

import com.trading.app.demo.dtos.UserUpdateRequestDTO;
import com.trading.app.demo.model.Profile;
import com.trading.app.demo.model.User;
import com.trading.app.demo.repository.ProfileRepository;
import com.trading.app.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Profile findByUserId(Long userId) {
        return profileRepository.findByUserId(userId)
                    .orElseThrow(() -> new IllegalStateException("Profile with Id=" + userId + " does not exist" ));
    }

    public void updateFullProfile(Long userId, UserUpdateRequestDTO updateInfo) {
        // find User

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User with Id=" + userId + " does not exist" ));
        // update user
        user.setEmail(updateInfo.getEmail());
        user.setPassword(passwordEncoder.encode(updateInfo.getPassword()));
        //save user
        userRepository.save(user);

        // find profile
        Profile profile = findByUserId(userId);
        // update profile
        profile.setFirstName(updateInfo.getFirst_name());
        profile.setLastName(updateInfo.getLast_name());
        profile.setAddress(updateInfo.getAddress());
        profile.setPhoneNumber(updateInfo.getPhone());
        // save profile
        profileRepository.save(profile);

    }
}
