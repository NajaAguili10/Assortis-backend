package com.backend.assorttis.dto.expert;

import com.backend.assorttis.dto.organization.OrganizationDTO;
import com.backend.assorttis.dto.sector.SectorDTO;
import com.backend.assorttis.entities.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
public class ExpertDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String phone;
    private String title;
    private String currentPosition;
    private Integer yearsExperience;
    private BigDecimal dailyRate;
    private BigDecimal hourlyRate;
    private String currency;
    private String availabilityStatus;
    private BigDecimal availabilityPercentage;
    private String profileSummary;
    private Long completedMissions;
    private Long completedProjects;
    private BigDecimal ratingAvg;
    private String verificationStatus;
    private String level;
    private OrganizationDTO.CountryDTO country;
    private OrganizationDTO.CityDTO city;
    private String primaryOrganizationName;
    private String   organizationId;
    private Boolean isBidWriter;
    private Boolean verified;
    private Instant lastActiveAt;
    
    private List<ExpertSectorDTO> sectors;
    private List<ExpertSkillDTO> skills;
    private List<ExpertLanguageDTO>languages;
    private List<ExpertEducationDTO>educations;
    private List<ExpertExperienceDTO>experiences;
    private List<ExpertCertificationDTO>certifications;



}
