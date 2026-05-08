package com.backend.assorttis.dto.partnership;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;

@Data
@Builder
public class PartnershipDTO {
    private Long id;
    private String organizationName;
    private String organizationType;
    private String partnerOrganizationName;
    private String partnerOrganizationType;
    private String status;
    private String type;
    private LocalDate startDate;
    private LocalDate endDate;
    private Instant createdAt;
    private String description;
    private String region;
    private String sector;
    private Long projectsCount;
}
