package com.backend.assorttis.dto.project;


import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProjectKPIsDTO {
    private Long totalProjects;
    private Long activeProjects;
    private Long completedProjects;
    private Long onHoldProjects;
    private BigDecimal totalBudget;
    private BigDecimal budgetSpent;
    private Integer averageCompletion;
    private Long urgentProjects;
}
