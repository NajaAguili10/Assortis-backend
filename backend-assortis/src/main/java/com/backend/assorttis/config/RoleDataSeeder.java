package com.backend.assorttis.config;

import com.backend.assorttis.entities.*;
import com.backend.assorttis.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Component
@RequiredArgsConstructor
@Order(0)
public class RoleDataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // 1. Create Roles if they don't exist
        Role expertRole = ensureRole("EXPERT", "Expert", "Role for individual experts");
        Role orgAdminRole = ensureRole("ORGANIZATION", "Organization Admin", "Role for organization administrators");
        Role orgUserRole = ensureRole("ORGANIZATION_USER", "Organization User", "Role for organization staff members");
        Role adminRole = ensureRole("ADMIN", "System Administrator", "Role for system administrators");
        Role publicRole = ensureRole("PUBLIC", "Public User", "Role for public/unauthenticated users");

        // 2. Create Test Users matching the previously commented out frontend test accounts
        ensureUser("expert@example.com", "password123", "John", "Expert", expertRole);
        ensureUser("organization@example.com", "password12345", "Jane", "OrgAdmin", orgAdminRole);
        ensureUser("organization-user@example.com", "password1234", "Bob", "OrgUser", orgUserRole);
        ensureUser("admin@example.com", "password1234", "System", "Admin", adminRole);
        ensureUser("public@example.com", "password123", "Generic", "User", publicRole);
    }

    private Role ensureRole(String code, String label, String description) {
        return roleRepository.findByCode(code).orElseGet(() -> {
            Role role = new Role()
                    .setCode(code)
                    .setLabel(label)
                    .setDescription(description)
                    .setIsSystem(true);
            return roleRepository.save(role);
        });
    }

    private void ensureUser(String email, String password, String firstName, String lastName, Role role) {
        userRepository.findByEmail(email).ifPresentOrElse(
            user -> {
                // Update password and roles to ensure consistency
                user.setPasswordHash(passwordEncoder.encode(password));
                userRepository.save(user);
                assignRoleIfMissing(user, role);
            },
            () -> {
                User user = new User()
                        .setEmail(email)
                        .setPasswordHash(passwordEncoder.encode(password))
                        .setFirstName(firstName)
                        .setLastName(lastName)
                        .setIsActive(true)
                        .setEmailVerified(true)
                        .setCreatedAt(Instant.now());
                user = userRepository.save(user);
                assignRoleIfMissing(user, role);
            }
        );
    }

    private void assignRoleIfMissing(User user, Role role) {
        UserRoleId id = new UserRoleId()
                .setUserId(user.getId())
                .setRoleId(role.getId())
                .setScopeType("GLOBAL")
                .setScopeId(0L);
        
        if (!userRoleRepository.existsById(id)) {
            UserRole userRole = new UserRole()
                    .setId(id)
                    .setUser(user)
                    .setRole(role)
                    .setAssignedAt(Instant.now());
            userRoleRepository.save(userRole);
        }
    }
}
