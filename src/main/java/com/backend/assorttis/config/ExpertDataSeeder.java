package com.backend.assorttis.config;

import com.backend.assorttis.entities.*;
import com.backend.assorttis.entities.enums.VerificationStatus;
import com.backend.assorttis.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Component
@RequiredArgsConstructor
@Order(3)
public class ExpertDataSeeder implements CommandLineRunner {

    private final ExpertRepository expertRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final OrganizationRepository organizationRepository;
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final SectorRepository sectorRepository;
    private final SkillRepository skillRepository;
    private final LanguageRepository languageRepository;
    private final ExpertSectorRepository expertSectorRepository;
    private final ExpertSkillRepository expertSkillRepository;
    private final ExpertLanguageRepository expertLanguageRepository;
    private final ExpertCertificationRepository expertCertificationRepository;
    private final PasswordEncoder passwordEncoder;

    private final AtomicLong countryIdCounter = new AtomicLong(1000);
    private final AtomicLong cityIdCounter = new AtomicLong(1000);
    private final AtomicLong skillIdCounter = new AtomicLong(1000);
    private final AtomicLong sectorIdCounter = new AtomicLong(1000);
    private final AtomicLong certIdCounter = new AtomicLong(1000);

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Role expertRole = roleRepository.findByCode("EXPERT")
                .orElseThrow(() -> new RuntimeException("Expert role not found"));

        Organization assortisTech = organizationRepository.findByName("Assortis Tech").orElse(null);
        Organization unicef = organizationRepository.findByName("UNICEF").orElse(null);
        Organization targetOrg = organizationRepository.findById(19L).orElse(null);

        // Expert 1: Sarah Johnson
        createExpert(
                "sarah.johnson@example.com", "Sarah", "Johnson", "Senior Project Manager", "Senior Project Manager", "British",
                12, 850, "EUR", "IMMEDIATE", 80.0,
                "Experienced project manager specializing in international development projects with a focus on infrastructure and governance.",
                4.8, VerificationStatus.ACTIVE, "SENIOR", true, "2024-02-20T10:30:00Z",
                targetOrg != null ? targetOrg : assortisTech, "United Kingdom", "GB", "London",
                List.of("INFRASTRUCTURE", "GOVERNANCE"),
                List.of("Project Management", "Stakeholder Engagement", "M&E", "Budget Management"),
                List.of(new LangInfo("EN", "English", "NATIVE"), new LangInfo("FR", "French", "FLUENT")),
                List.of(new CertInfo("PMP", "PMI", LocalDate.of(2020, 1, 1))),
                expertRole
        );

        // Expert 2: Ahmed Hassan
        createExpert(
                "ahmed.hassan@example.com", "Ahmed", "Hassan", "Agriculture Development Specialist", "Agriculture Development Specialist", "Egyptian",
                15, 750, "EUR", "WITHIN_1_MONTH", 70.0,
                "Agricultural economist with extensive experience in sustainable farming and rural development across Africa.",
                4.9, VerificationStatus.VERIFIED, "EXPERT", true, "2024-02-22T14:15:00Z",
                targetOrg != null ? targetOrg : assortisTech, "Egypt", "EG", "Cairo",
                List.of("AGRICULTURE", "ENVIRONMENT"),
                List.of("Agricultural Economics", "Rural Development", "Sustainable Farming", "Capacity Building"),
                List.of(new LangInfo("AR", "Arabic", "NATIVE"), new LangInfo("EN", "English", "FLUENT"), new LangInfo("FR", "French", "INTERMEDIATE")),
                List.of(new CertInfo("Sustainable Agriculture Cert", "FAO", LocalDate.of(2019, 5, 20))),
                expertRole
        );

        // Expert 3: Maria Garcia
        createExpert(
                "maria.garcia@example.com", "Maria", "Garcia", "Public Health Advisor", "Public Health Advisor", "Spanish",
                10, 680, "EUR", "IMMEDIATE", 90.0,
                "Public health specialist with focus on maternal health, disease prevention, and health systems strengthening.",
                4.7, VerificationStatus.ACTIVE, "SENIOR", true, "2024-02-23T09:45:00Z",
                assortisTech, "Spain", "ES", "Madrid",
                List.of("HEALTH", "EDUCATION"),
                List.of("Public Health", "Health Systems", "Epidemiology", "Training"),
                List.of(new LangInfo("ES", "Spanish", "NATIVE"), new LangInfo("EN", "English", "FLUENT"), new LangInfo("PT", "Portuguese", "ADVANCED")),
                List.of(new CertInfo("Certified Public Health Advisor", "WHO", LocalDate.of(2021, 3, 10))),
                expertRole
        );

        // Expert 4: Jean Dupont
        createExpert(
                "jean.dupont@example.com", "Jean", "Dupont", "Climate Change Consultant test", "Climate Change Consultant", "French",
                18, 950, "EUR", "WITHIN_3_MONTHS", 60.0,
                "Climate change expert specialized in adaptation strategies and environmental policy for developing countries.",
                5.0, VerificationStatus.ACTIVE, "LEAD", true, "2024-02-24T11:20:00Z",
                assortisTech, "France", "FR", "Paris",
                List.of("CLIMATE_CHANGE", "ENVIRONMENT", "ENERGY"),
                List.of("Climate Adaptation", "Environmental Policy", "Carbon Markets", "GIS"),
                List.of(new LangInfo("FR", "French", "NATIVE"), new LangInfo("EN", "English", "FLUENT")),
                List.of(new CertInfo("Climate Adaptation Expert Cert", "UNEP", LocalDate.of(2018, 11, 5))),
                expertRole
        );

        // Expert 5: Amara Ndiaye
        createExpert(
                "amara.ndiaye@example.com", "Amara", "Ndiaye", "Education Program Manager", "Education Program Manager", "Senegalese",
                9, 550, "EUR", "IMMEDIATE", 85.0,
                "Education specialist with expertise in curriculum development and teacher training in francophone Africa.",
                4.6, VerificationStatus.VERIFIED, "SENIOR", true, "2024-02-21T16:00:00Z",
                unicef, "Senegal", "SN", "Dakar",
                List.of("EDUCATION", "GOVERNANCE"),
                List.of("Curriculum Development", "Teacher Training", "Education Policy", "M&E"),
                List.of(new LangInfo("FR", "French", "NATIVE"), new LangInfo("WO", "Wolof", "NATIVE"), new LangInfo("EN", "English", "ADVANCED")),
                List.of(new CertInfo("Education Leadership Certificate", "UNESCO", LocalDate.of(2022, 2, 15))),
                expertRole
        );

        // Expert 6: David Ochieng
        createExpert(
                "david.ochieng@example.com", "David", "Ochieng", "Water & Sanitation Engineer", "Water & Sanitation Engineer", "Kenyan",
                7, 480, "EUR", "IMMEDIATE", 75.0,
                "Civil engineer specialized in water and sanitation infrastructure projects in rural and peri-urban areas.",
                4.5, VerificationStatus.ACTIVE, "INTERMEDIATE", false, "2024-02-23T13:30:00Z",
                assortisTech, "Kenya", "KE", "Nairobi",
                List.of("WATER_SANITATION", "INFRASTRUCTURE"),
                List.of("Civil Engineering", "WASH", "Project Design", "Community Engagement"),
                List.of(new LangInfo("EN", "English", "NATIVE"), new LangInfo("SW", "Swahili", "NATIVE")),
                List.of(new CertInfo("WASH Engineer Certification", "Red Cross", LocalDate.of(2020, 6, 30))),
                expertRole
        );

        // Expert 7: Margaret Thompson
        createExpert(
                "margaret.thompson@example.com", "Margaret", "Thompson", "Bid Writer & Proposal Manager", "Bid Writer & Proposal Manager", "American",
                13, 600, "EUR", "IMMEDIATE", 80.0,
                "Experienced bid writer and proposal manager with expertise in winning competitive tenders and grants for international development projects.",
                4.9, VerificationStatus.VERIFIED, "SENIOR", true, "2024-02-24T10:00:00Z",
                unicef, "United States", "US", "Washington DC",
                List.of("GOVERNANCE", "INFRASTRUCTURE"),
                List.of("Bid Writing", "Proposal Development", "Tender Response", "Grant Writing", "Document Management"),
                List.of(new LangInfo("EN", "English", "NATIVE"), new LangInfo("ES", "Spanish", "ADVANCED")),
                List.of(new CertInfo("Certified Proposal Manager (CP.APMP)", "APMP", LocalDate.of(2017, 9, 12))),
                expertRole
        );

        // Expert 8: Carlos Rodriguez
        createExpert(
                "carlos.rodriguez@example.com", "Carlos", "Rodriguez", "Tender Response Specialist", "Tender Response Specialist", "Colombian",
                8, 520, "EUR", "WITHIN_1_MONTH", 70.0,
                "Specialized bid writer focused on writing competitive proposals and tender documents for development and infrastructure projects.",
                4.7, VerificationStatus.ACTIVE, "INTERMEDIATE", false, "2024-02-22T15:45:00Z",
                assortisTech, "Colombia", "CO", "Bogotá",
                List.of("INFRASTRUCTURE", "AGRICULTURE"),
                List.of("Tender Writing", "Bid Proposal", "Technical Writing", "Compliance Documentation"),
                List.of(new LangInfo("ES", "Spanish", "NATIVE"), new LangInfo("EN", "English", "FLUENT"), new LangInfo("PT", "Portuguese", "INTERMEDIATE")),
                List.of(new CertInfo("International Tender Specialist", "EC", LocalDate.of(2021, 1, 25))),
                expertRole
        );

        // Expert 9: Patricia Williams
        createExpert(
                "patricia.williams@example.com", "Patricia", "Williams", "Grant Writer & Editor", "Grant Writer & Editor", "Canadian",
                11, 580, "EUR", "IMMEDIATE", 85.0,
                "Expert grant writer and editor with 11 years of experience writing successful proposals for multilateral donors and international NGOs.",
                4.8, VerificationStatus.VERIFIED, "SENIOR", true, "2024-02-23T11:30:00Z",
                unicef, "Canada", "CA", "Toronto",
                List.of("EDUCATION", "HEALTH"),
                List.of("Grant Writing", "Proposal Editing", "Donor Compliance", "Bid Strategy", "Technical Writer"),
                List.of(new LangInfo("EN", "English", "NATIVE"), new LangInfo("FR", "French", "FLUENT")),
                List.of(new CertInfo("Grant Writing Professional (GPC)", "GPA", LocalDate.of(2016, 8, 30))),
                expertRole
        );

        // Expert 10: Michael Chen
        createExpert(
                "michael.chen@example.com", "Michael", "Chen", "Technical Bid Writer", "Technical Bid Writer", "Singaporean",
                10, 640, "EUR", "IMMEDIATE", 90.0,
                "Technical bid writer specializing in complex tender responses for technology and infrastructure projects with proven track record of winning bids.",
                4.6, VerificationStatus.ACTIVE, "SENIOR", true, "2024-02-24T08:20:00Z",
                assortisTech, "Singapore", "SG", "Singapore",
                List.of("TECHNOLOGY", "INFRASTRUCTURE"),
                List.of("Technical Writing", "Bid Proposal", "Tender Strategy", "Project Documentation", "Writer"),
                List.of(new LangInfo("EN", "English", "NATIVE"), new LangInfo("ZH", "Mandarin", "FLUENT")),
                List.of(new CertInfo("Technical Writing Certification", "IEEE", LocalDate.of(2019, 12, 12))),
                expertRole
        );
    }

    private void createExpert(String email, String firstName, String lastName, String currentPosition, String title, String nationality,
                              Integer yearsExperience, Integer dailyRate, String currency,
                              String availabilityStatus, Double availabilityPercentage, String profileSummary,
                              Double ratingAvg, VerificationStatus verificationStatus, String level, Boolean verified,
                              String lastActive, Organization primaryOrg, String countryName, String countryCode,
                              String cityName, List<String> sectorCodes, List<String> skillNames,
                              List<LangInfo> languages, List<CertInfo> certifications, Role role) {

        User user = userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = new User()
                    .setEmail(email)
                    .setPasswordHash(passwordEncoder.encode("password123"))
                    .setFirstName(firstName)
                    .setLastName(lastName)
                    .setIsActive(true)
                    .setEmailVerified(true)
                    .setCreatedAt(Instant.now());
            newUser = userRepository.save(newUser);

            UserRole userRole = new UserRole();
            UserRoleId id = new UserRoleId()
                    .setUserId(newUser.getId())
                    .setRoleId(role.getId())
                    .setScopeType("GLOBAL")
                    .setScopeId(0L);
            userRole.setId(id);
            userRole.setUser(newUser);
            userRole.setRole(role);
            userRoleRepository.save(userRole);
            return newUser;
        });

        // Check if Expert already exists for this user
        if (expertRepository.findByUser(user).isPresent()) {
            return;
        }

        Country country = countryRepository.findByCode(countryCode).orElseGet(() -> {
            Country c = new Country().setName(countryName).setCode(countryCode);
            c.setId(countryIdCounter.getAndIncrement());
            return countryRepository.save(c);
        });

        City city = cityRepository.findByName(cityName).orElseGet(() -> {
            City ct = new City().setName(cityName);
            ct.setId(cityIdCounter.getAndIncrement());
            return cityRepository.save(ct);
        });

        Expert expert = new Expert()
                .setUser(user)
                .setFullName(firstName + " " + lastName)
                 .setTitle(title)
                .setNationality(nationality)
                .setCurrentPosition(currentPosition)
                .setYearsExperience(yearsExperience)
                .setDailyRate(BigDecimal.valueOf(dailyRate))
                .setCurrency(currency)
                .setAvailabilityStatus(availabilityStatus)
                .setAvailabilityPercentage(BigDecimal.valueOf(availabilityPercentage))
                .setProfileSummary(profileSummary)
                .setRatingAvg(BigDecimal.valueOf(ratingAvg))
                .setVerificationStatus(verificationStatus)
                .setLevel(level)
                .setCountry(country)
                .setCity(city)
                .setPrimaryOrganization(primaryOrg)
                .setLastActiveAt(Instant.parse(lastActive))
                .setVisibility(true)
                .setCreatedAt(Instant.now())
                .setUpdatedAt(Instant.now());

        // Check if expert has Bid Writer skills
        boolean isBidWriter = currentPosition.toLowerCase().contains("bid writer") || 
                             currentPosition.toLowerCase().contains("proposal") ||
                             skillNames.stream().anyMatch(s -> s.toLowerCase().contains("bid") || s.toLowerCase().contains("proposal"));
        expert.setIsBidWriter(isBidWriter);

        expert = expertRepository.save(expert);

        // Sectors removed to prevent unwanted data creation

        // Skills
        for (String skillName : skillNames) {
            final String name = skillName;
            Skill skill = skillRepository.findByName(name).orElseGet(() -> {
                Skill s = new Skill().setName(name);
                s.setId(skillIdCounter.getAndIncrement());
                return skillRepository.save(s);
            });
            ExpertSkillId skId = new ExpertSkillId()
                    .setExpertId(expert.getId())
                    .setSkillId(skill.getId());
            if (!expertSkillRepository.existsById(skId)) {
                ExpertSkill es = new ExpertSkill()
                        .setId(skId)
                        .setExpert(expert)
                        .setSkill(skill)
                        .setLevel("Expert");
                expertSkillRepository.save(es);
            }
        }

        // Languages
        for (LangInfo langInfo : languages) {
            Language language = languageRepository.findById(langInfo.code).orElseGet(() -> {
                Language l = new Language()
                        .setCode(langInfo.code)
                        .setName(langInfo.name)
                        .setNativeName(langInfo.name)
                        .setIsActive(true);
                return languageRepository.save(l);
            });
            ExpertLanguageId elId = new ExpertLanguageId()
                    .setExpertId(expert.getId())
                    .setLanguageCode(language.getCode());
            if (!expertLanguageRepository.existsById(elId)) {
                ExpertLanguage el = new ExpertLanguage()
                        .setId(elId)
                        .setExpert(expert)
                        .setLanguageCode(language)
                        .setProficiency(langInfo.level);
                expertLanguageRepository.save(el);
            }
        }

        // Certifications
        for (CertInfo certInfo : certifications) {
            ExpertCertification cert = new ExpertCertification()
                    .setId(certIdCounter.getAndIncrement())
                    .setExpert(expert)
                    .setName(certInfo.name)
                    .setIssuingOrganization(certInfo.issuer)
                    .setIssueDate(certInfo.issueDate)
                    .setExpiryDate(certInfo.issueDate.plusYears(5));
            expertCertificationRepository.save(cert);
        }
    }

    private static class LangInfo {
        String code;
        String name;
        String level;
        LangInfo(String code, String name, String level) {
            this.code = code;
            this.name = name;
            this.level = level;
        }
    }

    private static class CertInfo {
        String name;
        String issuer;
        LocalDate issueDate;
        CertInfo(String name, String issuer, LocalDate issueDate) {
            this.name = name;
            this.issuer = issuer;
            this.issueDate = issueDate;
        }
    }
}
