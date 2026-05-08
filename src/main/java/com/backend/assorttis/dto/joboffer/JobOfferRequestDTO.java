package com.backend.assorttis.dto.joboffer;

import lombok.Data;

import java.time.LocalDate;

@Data
public class JobOfferRequestDTO {
    private String title;
    private String description;
    private String contractType;
    private LocalDate deadline;
    private String status;
    private String type;
    private String projectTitle;
    private String department;
    private String location;
    private String contactEmail;
    private String contactPerson;
    private Long organizationId;
}
