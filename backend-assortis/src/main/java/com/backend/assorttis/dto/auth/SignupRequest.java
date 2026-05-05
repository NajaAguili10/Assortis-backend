package com.backend.assorttis.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.List;

@Data
public class SignupRequest {
    @NotBlank
    private String accountType; // "organization" or "expert"
    private String planType;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
    
    private String confirmPassword;

    // Organization fields
    private String orgName;
    private String orgLegalName;
    private String orgType;
    private String orgRegistrationNumber;
    private String orgEmail;
    private String orgPhone;
    private String orgWebsite;
    private String orgCountry;
    private String orgCity;
    private String contactPersonName;
    private String contactPersonPosition;
    private String orgSectors;
    private String orgSubsectors;

    // Expert fields
    private String firstName;
    private String lastName;
    private String expertEmail;
    private String expertPhone;
    private String nationality;
    private String country;
    private String city;
    private String expertise;
    private String expertSubsectors;
    private String experience;

    // Newsletter preferences
    private Boolean newsletterTenders;
    private Boolean newsletterTraining;
    private Boolean newsletterJobs;

    // Subscription configuration
    private List<String> subscriptionSectors;
    private List<String> subscriptionCountries;
    private Double subscriptionPrice;
}
