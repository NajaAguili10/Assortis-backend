package com.backend.assorttis.service;

import com.backend.assorttis.dto.auth.SignupRequest;
import com.backend.assorttis.entities.*;
import com.backend.assorttis.entities.enums.VerificationStatus;
import com.backend.assorttis.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final OrganizationRepository organizationRepository;
    private final ExpertRepository expertRepository;
    private final PasswordEncoder passwordEncoder;

    private final Map<String, String> verificationCodes = new ConcurrentHashMap<>();

    public void sendVerificationCode(String email) {
        final String normalizedEmail = email.toLowerCase().trim();
        String code = String.format("%06d", new Random().nextInt(999999));
        verificationCodes.put(normalizedEmail, code);
        // Simulate sending email by logging it
        System.out.println("Verification code for " + normalizedEmail + " is " + code + " (Use the latest one if multiple appear)");
    }

    @Transactional
    public void verifyEmailCode(String email, String code) {
        final String normalizedEmail = email.toLowerCase().trim();
        String storedCode = verificationCodes.get(normalizedEmail);
        if (storedCode == null) {
            throw new RuntimeException("No verification code found or code expired.");
        }
        if (!storedCode.equals(code)) {
            throw new RuntimeException("The code you entered is incorrect.");
        }
        
        verificationCodes.remove(normalizedEmail);
        
        // Update user if they exist in the DB (for post-registration verification).
        // If they don't exist yet (pre-registration), we just successfully consumed the code!
        userRepository.findByEmail(normalizedEmail).ifPresent(user -> {
            user.setEmailVerified(true);
            userRepository.save(user);
        });
        
        verificationCodes.remove(normalizedEmail);
    }

    @Transactional
    public User registerUser(SignupRequest signUpRequest) {
        final String normalizedEmail = signUpRequest.getEmail() != null ? signUpRequest.getEmail().toLowerCase().trim() : null;
        
        if (userRepository.findByEmail(normalizedEmail).isPresent()) {
            throw new RuntimeException("Error: Email is already in use!");
        }

        User user = new User()
            .setEmail(normalizedEmail)
            .setPasswordHash(passwordEncoder.encode(signUpRequest.getPassword()))
            .setIsActive(true)
            .setEmailVerified(false)
            .setNewsletterOptIn(signUpRequest.getNewsletterTenders() != null ? signUpRequest.getNewsletterTenders() : false)
            .setCreatedAt(Instant.now());

        if ("organization".equalsIgnoreCase(signUpRequest.getAccountType())) {
            String name = signUpRequest.getContactPersonName() != null ? signUpRequest.getContactPersonName() : signUpRequest.getOrgName();
            if (name != null && name.contains(" ")) {
                user.setFirstName(name.substring(0, name.indexOf(" ")));
                user.setLastName(name.substring(name.indexOf(" ") + 1));
            } else {
                user.setFirstName(name);
            }
            user.setPhone(signUpRequest.getOrgPhone());
        } else {
            user.setFirstName(signUpRequest.getFirstName());
            user.setLastName(signUpRequest.getLastName());
            user.setPhone(signUpRequest.getExpertPhone());
        }

        userRepository.save(user);

        String roleCode = "organization".equalsIgnoreCase(signUpRequest.getAccountType()) ? "ORGANIZATION" : "EXPERT";
        Role role = roleRepository.findByCode(roleCode)
            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        UserRoleId userRoleId = new UserRoleId();
        userRoleId.setUserId(user.getId());
        userRoleId.setRoleId(role.getId());
        userRoleId.setScopeType("GLOBAL");
        userRoleId.setScopeId(0L);

        UserRole userRole = new UserRole()
            .setId(userRoleId)
            .setUser(user)
            .setRole(role);
        userRoleRepository.save(userRole);

        if ("organization".equalsIgnoreCase(signUpRequest.getAccountType())) {
            Organization org = new Organization();
            org.setName(signUpRequest.getOrgName() != null ? signUpRequest.getOrgName() : "Unknown");
            org.setLegalName(signUpRequest.getOrgLegalName());
            org.setType(signUpRequest.getOrgType());
            org.setRegistrationNumber(signUpRequest.getOrgRegistrationNumber());
            org.setContactEmail(signUpRequest.getOrgEmail() != null ? signUpRequest.getOrgEmail().toLowerCase().trim() : normalizedEmail);
            org.setContactPhone(signUpRequest.getOrgPhone());
            org.setWebsite(signUpRequest.getOrgWebsite());
            org.setContactName(signUpRequest.getContactPersonName());
            org.setContactTitle(signUpRequest.getContactPersonPosition());
            org.setVerificationStatus(VerificationStatus.PENDING);
            org.setCreatedAt(Instant.now());
            org.setIsActive(true);
            
            organizationRepository.save(org);
        } else if ("expert".equalsIgnoreCase(signUpRequest.getAccountType())) {
            Expert expert = new Expert();
            expert.setUser(user);
            String fullName = (signUpRequest.getFirstName() != null ? signUpRequest.getFirstName() : "") + 
                              (signUpRequest.getLastName() != null ? " " + signUpRequest.getLastName() : "");
            expert.setFullName(fullName.trim());
            expert.setNationality(signUpRequest.getNationality());
            expert.setVerificationStatus(VerificationStatus.PENDING);
            expert.setCreatedAt(Instant.now());
            expert.setVisibility(true);
            
            try {
                if (signUpRequest.getExperience() != null) {
                    expert.setYearsExperience(Integer.parseInt(signUpRequest.getExperience()));
                }
            } catch (NumberFormatException ignored) {}

            expertRepository.save(expert);
        }

        return user;
    }
}
