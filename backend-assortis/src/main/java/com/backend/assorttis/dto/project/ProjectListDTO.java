package com.backend.assorttis.dto.project;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class ProjectListDTO {
    private Long id;
    private String code;
    private String title;
    private String description;
    private String status;
    private String priority;
    private String type;

    // Location
    private String country;
    private String region; // déjà présent dans Project

    // Sector
    private String sector; // mainSector name
    private List<String> subsectors; // list of subsector names

    // Budget
    private ProjectBudgetDTO budget; // inner class

    // Timeline
    private ProjectTimelineDTO timeline;

    // Organisation
    private String leadOrganization; // name of lead org
    private List<String> partners; // names of partner orgs
    private Integer teamSize;

    // Tasks stats
    private Integer tasksCompleted;
    private Integer totalTasks;

    // Dates
    private LocalDate createdDate;   // from createdAt? Project has updatedAt but no createdDate; we can use createdAt from an audit field if available. For now we might omit or derive.
    private LocalDate updatedDate;   // from updatedAt

    // inner DTOs
    @Data
    public static class ProjectBudgetDTO {
        private BigDecimal total;
        private BigDecimal spent;
        private BigDecimal remaining;
        private String currency;
    }

    @Data
    public static class ProjectTimelineDTO {
        private LocalDate startDate;
        private LocalDate endDate;
        private Integer duration; // in months
        private Integer completionPercentage; // 0-100
    }
}
