package com.backend.assorttis.controller;

import com.backend.assorttis.entities.User;
import com.backend.assorttis.repository.UserRepository;
import com.backend.assorttis.security.jwt.JwtUtils;
import com.backend.assorttis.security.services.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/dev/auth")
@RequiredArgsConstructor
public class DevAuthController {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    @PostMapping("/token")
    public ResponseEntity<Map<String, Object>> generateDevToken(@RequestParam Long userId) {

        System.out.println("DEV AUTH HIT");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found with id: " + userId
                ));

        UserDetailsImpl userDetails = UserDetailsImpl.build(user, Collections.emptyList());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        String token = jwtUtils.generateJwtToken(authentication);

        return ResponseEntity.ok(Map.of(
                "token", token,
                "type", "Bearer",
                "userId", user.getId(),
                "email", user.getEmail()
        ));
    }
}
