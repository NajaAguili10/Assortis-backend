package com.backend.assorttis.dto.organization;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrganizationProjectReferenceDTO {
    private Long id;
    private Long organizationId;
    private String referenceNumber;
    private String title;
    private String summary;
    private String description;
    private String country;
    private String countryName;
    private String region;
    private String regionLabel;
    private String sector;
    private String sectorName;
    private String subSector;
    private String subSectorName;
    private String client;
    private String donor;
    private String donorName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String referenceType;
    private String url;
    private List<OrganizationProjectReferenceDocumentDTO> documents = new ArrayList<>();
    private Instant createdAt;
    private Instant updatedAt;
}
