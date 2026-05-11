package com.backend.assorttis.controller;

import com.backend.assorttis.dto.auth.JwtResponse;
import com.backend.assorttis.dto.auth.LoginRequest;
import com.backend.assorttis.security.jwt.JwtUtils;
import com.backend.assorttis.security.services.UserDetailsImpl;
import com.backend.assorttis.dto.auth.SignupRequest;
import com.backend.assorttis.dto.auth.EmailVerificationRequest;
import com.backend.assorttis.dto.auth.VerifyEmailRequest;
import com.backend.assorttis.entities.User;
import com.backend.assorttis.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthenticationManager authenticationManager;
  private final JwtUtils jwtUtils;
  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();    
    List<String> authorities = userDetails.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());

    List<String> roles = authorities.stream()
        .filter(a -> a.startsWith("ROLE_"))
        .map(a -> a.replace("ROLE_", ""))
        .collect(Collectors.toList());
        
    List<String> permissions = authorities.stream()
        .filter(a -> !a.startsWith("ROLE_"))
        .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(jwt, 
                         userDetails.getId(), 
                         userDetails.getEmail(), 
                         roles,
                         permissions
                       ));
  }

  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    try {
        authService.registerUser(signUpRequest);
        return ResponseEntity.ok("User registered successfully!");
    } catch (RuntimeException e) {
        return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
    }
  }

  @PostMapping("/send-verification")
  public ResponseEntity<?> sendVerification(@Valid @RequestBody EmailVerificationRequest request) {
    try {
        authService.sendVerificationCode(request.getEmail());
        return ResponseEntity.ok(Map.of("message", "Verification email sent"));
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
    }
  }

  @PostMapping("/verify-email")
  public ResponseEntity<?> verifyEmail(@Valid @RequestBody VerifyEmailRequest request) {
    try {
        authService.verifyEmailCode(request.getEmail(), request.getCode());
        return ResponseEntity.ok(Map.of("message", "Email verified successfully"));
    } catch (RuntimeException e) {
        return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
    }
  }

  @PostMapping("/forgot-password")
  public ResponseEntity<?> forgotPassword(@Valid @RequestBody com.backend.assorttis.dto.auth.ForgotPasswordRequest request) {
    try {
        authService.initiatePasswordReset(request.getEmail());
        return ResponseEntity.ok(Map.of("message", "Password reset email sent"));
    } catch (Exception e) {
        // Return OK even if email not found for security, or return error if preferred
        return ResponseEntity.ok(Map.of("message", "If an account exists, a reset link has been sent."));
    }
  }

  @PostMapping("/reset-password")
  public ResponseEntity<?> resetPassword(@Valid @RequestBody com.backend.assorttis.dto.auth.ResetPasswordRequest request) {
    try {
        authService.resetPassword(request.getToken(), request.getNewPassword());
        return ResponseEntity.ok(Map.of("message", "Password reset successfully"));
    } catch (RuntimeException e) {
        return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
    }
  }
}
