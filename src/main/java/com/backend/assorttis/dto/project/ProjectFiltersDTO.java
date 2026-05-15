package com.backend.assorttis.dto.project;

import com.backend.assorttis.entities.enums.project.ProjectPriorityEnum;
import com.backend.assorttis.entities.enums.project.ProjectStatus;
import com.backend.assorttis.entities.enums.project.ProjectTypeEnum;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class ProjectFiltersDTO {
    private String searchQuery;
    private List<ProjectStatus> status;
    private List<ProjectPriorityEnum> priority;
    private List<ProjectTypeEnum> type;
    private List<String> sector;
    private List<String> subsector;
    private List<String> region;
    private BigDecimal minBudget;
    private BigDecimal maxBudget;
    private LocalDate startDateFrom;
    private LocalDate startDateTo;
    private LocalDate endDateFrom;
    private LocalDate endDateTo;
    private List<String> leadOrganization;
    private List<String> partners;
}
