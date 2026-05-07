package com.backend.assorttis.dto.organization;

import com.backend.assorttis.dto.sector.SubsectorDTO;
import com.backend.assorttis.entities.OrganizationCertification;
import lombok.Data;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
public class OrganizationDTO {
    private Long id;
    private String name;
    private String cleanName;
    private String legalName;
    private String type;
    private String registrationNumber;
    private Integer yearFounded;
    private Integer employeesCount;
    private BigDecimal annualTurnover;
    private String website;
    private String logoUrl;
    private String description;
    private Boolean validated;
    private Instant verifiedAt;
    private BigDecimal ratingAvg;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Boolean isPartner;
    private String slogan;
    private String contactEmail;
    private String contactPhone;
    private String address;
    private String verificationStatus;
    private String profileValidationStatus;
    private String acronym;
    private String region;
    private Boolean isActive;
    private String postalCode;
    private String equipmentInfrastructure;
    private String contactName;
    private String contactTitle;

    // Location details
    private CountryDTO country;
    private CityDTO city;

    // Administrative details
    private Long parentId;
    private SectorDTO mainSector;
    private String defaultPaymentMethod;
    private Instant createdAt;
    private Instant updatedAt;
    private String profileValidatedBy;
    private Instant profileValidatedAt;
    private Long activeProjects;
    private Long partnerships;
    private List<OrganizationCertificationDTO> certifications ;
    private BigDecimal budget;
    private Long completedProjects;
    private Long teamMembers;
    private List<SectorDTO> sectors;
    private List<SubsectorDTO> subsectors;
    private List<String> languages;
    private List<String> services;
    private List<String> operatingRegions;
    private List<String> partnershipNames;

    @Data
    public static class CountryDTO {
        private Long id;
        private String name;
        private String code;
    }

    @Data
    public static class CityDTO {
        private Long id;
        private String name;
    }

    @Data
    public static class SectorDTO {
        private Long id;
        private String name;
        private String code;
    }
}
