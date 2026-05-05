package com.backend.assorttis.dto.joboffer;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;

@Data
@Builder
public class JobOfferResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String contractType;
    private LocalDate deadline;
    private String status;
    private Instant createdAt;
    private Instant updatedAt;
    private Long organizationId;
    private String organizationName;
    private Long projectId;
    private String projectTitle;
    private String type;
    private String location;
    private String department;
    private String contactEmail;
    private String contactPerson;
    private Integer applicationsCount;
    private Integer totalApplications;
}
