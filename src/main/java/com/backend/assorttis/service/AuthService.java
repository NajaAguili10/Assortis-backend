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
    private final MailService mailService;
    private final OrganizationRepository organizationRepository;
    private final ExpertRepository expertRepository;
    private final SectorRepository sectorRepository;
    private final CountryRepository countryRepository;
    private final ExpertSubscriptionSectorRepository expertSubscriptionSectorRepository;
    private final ExpertSubscriptionCountryRepository expertSubscriptionCountryRepository;
    private final OrganizationSubscriptionSectorRepository organizationSubscriptionSectorRepository;
    private final OrganizationSubscriptionCountryRepository organizationSubscriptionCountryRepository;
    private final OrganizationUserRepository organizationUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final CityRepository cityRepository;
    private  final CountryRepository CountryRepository;

    private final Map<String, String> verificationCodes = new ConcurrentHashMap<>();
    private final Map<String, String> resetTokens = new ConcurrentHashMap<>();

    public void initiatePasswordReset(String email) {
        final String normalizedEmail = email.toLowerCase().trim();
        userRepository.findByEmail(normalizedEmail)
            .orElseThrow(() -> new RuntimeException("No account found with this email address."));
        
        String token = java.util.UUID.randomUUID().toString();
        resetTokens.put(token, normalizedEmail);
        
        try {
            mailService.sendPasswordResetEmail(normalizedEmail, token);
        } catch (Exception e) {
            // Log error but don't fail the request
        }
    }

    @Transactional
    public void resetPassword(String token, String newPassword) {
        String email = resetTokens.get(token);
        if (email == null) {
            throw new RuntimeException("Invalid or expired reset token.");
        }
        
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found."));
            
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        
        resetTokens.remove(token);
    }

    public void sendVerificationCode(String email) {
        final String normalizedEmail = email.toLowerCase().trim();
        String code = String.format("%06d", new Random().nextInt(999999));
        verificationCodes.put(normalizedEmail, code);
        
        // Send real email

        // Attempt real email (will fallback to console if SMTP disabled)
        try {
            mailService.sendVerificationEmail(normalizedEmail, code);
        } catch (Exception e) {
            // Silently fail if SMTP is disabled, as we already logged to console
        }
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

    private Country findCountry(String val) {
        if (val == null || val.trim().isEmpty()) return null;
        String trimmed = val.trim();
        return countryRepository.findByNameIgnoreCase(trimmed)
                .or(() -> countryRepository.findByName(trimmed))
                .or(() -> countryRepository.findByCode(trimmed))
                .or(() -> {
                    try {
                        return countryRepository.findById(Long.parseLong(trimmed));
                    } catch (Exception e) {
                        return java.util.Optional.empty();
                    }
                })
                .orElse(null);
    }

    private City findCity(String val) {
        if (val == null || val.trim().isEmpty()) return null;
        String trimmed = val.trim();
        return cityRepository.findByNameIgnoreCase(trimmed)
                .or(() -> cityRepository.findByName(trimmed))
                .or(() -> {
                    try {
                        return cityRepository.findById(Long.parseLong(trimmed));
                    } catch (Exception e) {
                        return java.util.Optional.empty();
                    }
                })
                .orElse(null);
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

        // Set User Location (Robust)
        if ("organization".equalsIgnoreCase(signUpRequest.getAccountType())) {
            user.setCountry(findCountry(signUpRequest.getOrgCountry()));
            user.setCity(findCity(signUpRequest.getOrgCity()));
        } else {
            user.setCountry(findCountry(signUpRequest.getCountry()));
            user.setCity(findCity(signUpRequest.getCity()));
        }

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
            
            // Set Organization Location (Robust)
            Country c = findCountry(signUpRequest.getOrgCountry());
            City ct = findCity(signUpRequest.getOrgCity());
            
            if (c != null) {
                System.out.println("DEBUG: Setting country for org: " + c.getName());
                org.setCountry(c);
            }
            if (ct != null) {
                System.out.println("DEBUG: Setting city for org: " + ct.getName());
                org.setCity(ct);
            }
            
            final Organization savedOrg = organizationRepository.saveAndFlush(org);

            // Link user to organization
            OrganizationUserId ouId = new OrganizationUserId();
            ouId.setOrganizationId(savedOrg.getId());
            ouId.setUserId(user.getId());

            OrganizationUser ou = new OrganizationUser();
            ou.setId(ouId);
            ou.setOrganization(savedOrg);
            ou.setUser(user);
            ou.setRole("ADMIN"); // Default role
            ou.setIsAdmin(true);
            ou.setMembershipStatus("active");
            ou.setJoinedAt(Instant.now());
            organizationUserRepository.save(ou);

            // Save subscription sectors
            if (signUpRequest.getSubscriptionSectors() != null) {
                System.out.println("DEBUG: Processing " + signUpRequest.getSubscriptionSectors().size() + " subscription sectors for organization");
                for (String sectorCode : signUpRequest.getSubscriptionSectors()) {
                    sectorRepository.findByCode(sectorCode).ifPresentOrElse(sector -> {
                        OrganizationSubscriptionSectorId id = new OrganizationSubscriptionSectorId();
                        id.setOrganizationId(savedOrg.getId());
                        id.setSectorId(sector.getId());

                        OrganizationSubscriptionSector oss = new OrganizationSubscriptionSector();
                        oss.setId(id);
                        oss.setOrganization(savedOrg);
                        oss.setSector(sector);
                        organizationSubscriptionSectorRepository.saveAndFlush(oss);
                    }, () -> System.out.println("DEBUG: Sector code not found: " + sectorCode));
                }
            }

            // Save subscription countries
            if (signUpRequest.getSubscriptionCountries() != null) {
                System.out.println("Registering organization subscription countries: " + signUpRequest.getSubscriptionCountries());
                for (String countryCode : signUpRequest.getSubscriptionCountries()) {
                    countryRepository.findByCode(countryCode).ifPresentOrElse(country -> {
                        System.out.println("Saving organization subscription country: " + countryCode);
                        OrganizationSubscriptionCountryId id = new OrganizationSubscriptionCountryId();
                        id.setOrganizationId(savedOrg.getId());
                        id.setCountryId(country.getId());

                        OrganizationSubscriptionCountry osc = new OrganizationSubscriptionCountry();
                        osc.setId(id);
                        osc.setOrganization(savedOrg);
                        osc.setCountry(country);
                        organizationSubscriptionCountryRepository.saveAndFlush(osc);
                    }, () -> {
                        System.out.println("DEBUG: Country code not found: " + countryCode);
                    });
                }
            }
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
            
            // Set Expert Location (Robust)
            Country c = findCountry(signUpRequest.getCountry());
            City ct = findCity(signUpRequest.getCity());
            
            if (c != null) {
                System.out.println("DEBUG: Setting country for expert: " + c.getName());
                expert.setCountry(c);
            }
            if (ct != null) {
                System.out.println("DEBUG: Setting city for expert: " + ct.getName());
                expert.setCity(ct);
            }
            
            try {
                if (signUpRequest.getExperience() != null) {
                    expert.setYearsExperience(Integer.parseInt(signUpRequest.getExperience()));
                }
            } catch (NumberFormatException ignored) {}

            final Expert savedExpert = expertRepository.save(expert);

            // Save subscription sectors
            if (signUpRequest.getSubscriptionSectors() != null) {
                System.out.println("DEBUG: Processing " + signUpRequest.getSubscriptionSectors().size() + " subscription sectors for organization");
                for (String sectorCode : signUpRequest.getSubscriptionSectors()) {
                    sectorRepository.findByCode(sectorCode).ifPresentOrElse(sector -> {
                        ExpertSubscriptionSectorId id = new ExpertSubscriptionSectorId();
                        id.setExpertId(savedExpert.getId());
                        id.setSectorId(sector.getId());

                        ExpertSubscriptionSector ess = new ExpertSubscriptionSector();
                        ess.setId(id);
                        ess.setExpert(savedExpert);
                        ess.setSector(sector);
                        expertSubscriptionSectorRepository.saveAndFlush(ess);
                    }, () -> System.out.println("DEBUG: Sector code not found: " + sectorCode));
                }
            }

            // Save subscription countries
            if (signUpRequest.getSubscriptionCountries() != null) {
                System.out.println("Registering expert subscription countries: " + signUpRequest.getSubscriptionCountries());
                for (String countryCode : signUpRequest.getSubscriptionCountries()) {
                    countryRepository.findByCode(countryCode).ifPresentOrElse(country -> {
                        System.out.println("Saving expert subscription country: " + countryCode);
                        ExpertSubscriptionCountryId id = new ExpertSubscriptionCountryId();
                        id.setExpertId(savedExpert.getId());
                        id.setCountryId(country.getId());

                        ExpertSubscriptionCountry esc = new ExpertSubscriptionCountry();
                        esc.setId(id);
                        esc.setExpert(savedExpert);
                        esc.setCountry(country);
                        expertSubscriptionCountryRepository.saveAndFlush(esc);
                    }, () -> {
                        System.out.println("DEBUG: Country code not found: " + countryCode);
                    });
                }
            }
        }

        return user;
    }
}
