package com.backend.assorttis.dto.project;

import com.backend.assorttis.entities.enums.project.ProjectStatus;
import com.backend.assorttis.entities.enums.project.ProjectTypeEnum;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class ProjectListDTO {
    private Long id;
    private String referenceCode;
    private String code;
    private String title;
    private String name; // Alias for title
    private String description;
    private String status;
    private String priority;
    private String type;

    // Location
    private String country;
    private String city;
    private String region;

    // Sector
    private String sector;
    private List<String> subsectors;

    // Budget
    private ProjectBudgetDTO budget;
    private String currency;

    // Organization
    private DonorDTO donor;
    private String fundingType;
    private String leadOrganization;
    private List<String> partners;
    private Integer teamSize;

    // Timeline
    private ProjectTimelineDTO timeline;
    private String startDate; // ISO String
    private String endDate;   // ISO String

    // Tasks stats
    private Integer tasksCompleted;
    private Integer totalTasks;

    // Audit
    private String updatedAt; // ISO String

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
        private Integer duration;
        private Integer completionPercentage;
    }


    @Data
    public static class DonorDTO {
        private Long id;
        private String name;
        private String shortName;
        private String type;
    }
}
