package com.backend.assorttis.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "project_tasks", schema = "public", indexes = {
        @Index(name = "idx_project_tasks_project", columnList = "project_id"),
        @Index(name = "idx_project_tasks_status", columnList = "status"),
        @Index(name = "idx_project_tasks_due_date", columnList = "due_date")
})
public class ProjectTask {
    private Long id;

    private Project project;

    private String title;

    private String description;

    private String status;

    private String priority;

    private Map<String, Object> assignedTo;

    private LocalDate dueDate;

    private LocalDate completedDate;

    private Map<String, Object> tags;

    private Instant createdAt;

    private Instant updatedAt;

    private String taskCode;

    private LocalDate startDate;

    private Integer estimatedHours;

    private Boolean isMilestone;

    private BigDecimal estimatedBudget;

    private String resourcesRequired;

    private Map<String, Object> deliverables;

    private String category;

    private String complexity;

    private ProjectTask parentTask;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "project_id", nullable = false)
    public Project getProject() {
        return project;
    }

    @Size(max = 255)
    @NotNull
    @Column(name = "title", nullable = false)
    public String getTitle() {
        return title;
    }

    @Column(name = "description", length = Integer.MAX_VALUE)
    public String getDescription() {
        return description;
    }

    @Size(max = 50)
    @ColumnDefault("'TODO'")
    @Column(name = "status", length = 50)
    public String getStatus() {
        return status;
    }

    @Size(max = 20)
    @Column(name = "priority", length = 20)
    public String getPriority() {
        return priority;
    }

    @Column(name = "assigned_to")
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getAssignedTo() {
        return assignedTo;
    }

    @Column(name = "due_date")
    public LocalDate getDueDate() {
        return dueDate;
    }

    @Column(name = "completed_date")
    public LocalDate getCompletedDate() {
        return completedDate;
    }

    @Column(name = "tags")
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getTags() {
        return tags;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    public Instant getUpdatedAt() {
        return updatedAt;
    }

    @Size(max = 100)
    @Column(name = "task_code", length = 100)
    public String getTaskCode() {
        return taskCode;
    }

    @Column(name = "start_date")
    public LocalDate getStartDate() {
        return startDate;
    }

    @Column(name = "estimated_hours")
    public Integer getEstimatedHours() {
        return estimatedHours;
    }

    @ColumnDefault("false")
    @Column(name = "is_milestone")
    public Boolean getIsMilestone() {
        return isMilestone;
    }

    @Column(name = "estimated_budget")
    public BigDecimal getEstimatedBudget() {
        return estimatedBudget;
    }

    @Column(name = "resources_required", length = Integer.MAX_VALUE)
    public String getResourcesRequired() {
        return resourcesRequired;
    }

    @Column(name = "deliverables")
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getDeliverables() {
        return deliverables;
    }

    @Size(max = 100)
    @Column(name = "category", length = 100)
    public String getCategory() {
        return category;
    }

    @Size(max = 50)
    @Column(name = "complexity", length = 50)
    public String getComplexity() {
        return complexity;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "parent_task_id")
    public ProjectTask getParentTask() {
        return parentTask;
    }

}
