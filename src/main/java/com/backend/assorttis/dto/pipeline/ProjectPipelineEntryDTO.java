package com.backend.assorttis.dto.pipeline;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Data
public class ProjectPipelineEntryDTO {
    private Long tenderId;
    private Long memberOrganizationId;
    private String memberOrganizationName;
    private List<String> memberUserNames;
    private String tenderTitle;
    private String tenderReference;
    private String publishedByOrganizationName;
    private String country;
    private String donor;
    private String status;
    private String stage;
    private String priority;
    private String role;
    private String roleSought;
    private String notes;
    private Integer progressPercent;
    private BigDecimal probability;
    private BigDecimal expectedValue;
    private String currency;
    private BigDecimal matchScore;
    private Boolean aiRecommendation;
    private LocalDate deadline;
    private Instant addedAt;
    private Instant updatedAt;
    private List<String> sectors;
}
