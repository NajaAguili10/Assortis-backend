package com.backend.assorttis.dto.organization;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrganizationProjectReferenceRequest {
    private String referenceNumber;
    private String title;
    private String summary;
    private String description;
    private String country;
    private String region;
    private String sector;
    private String subSector;
    private String client;
    private String donor;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String referenceType;
    private String url;
    private List<OrganizationProjectReferenceDocumentDTO> documents = new ArrayList<>();
}
