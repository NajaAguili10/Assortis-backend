package com.backend.assorttis.dto.project;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class ProjectFiltersDTO {
    private String searchQuery;
    private List<String> status;
    private List<String> priority;
    private List<String> type;
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
