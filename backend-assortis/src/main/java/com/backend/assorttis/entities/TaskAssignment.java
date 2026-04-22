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
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "task_assignments", schema = "public", indexes = {
        @Index(name = "idx_task_assignments_task", columnList = "task_id"),
        @Index(name = "idx_task_assignments_expert", columnList = "expert_id")
}, uniqueConstraints = {
        @UniqueConstraint(name = "task_assignments_task_id_expert_id_key", columnNames = { "task_id", "expert_id" })
})
public class TaskAssignment {
    private Long id;

    private ProjectTask task;

    private Expert expert;

    private String role;

    private BigDecimal allocationPercentage;

    private LocalDate startDate;

    private LocalDate endDate;

    private String responsibilityLevel;

    private String notes;

    private Instant createdAt;

    private Instant updatedAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "task_id", nullable = false)
    public ProjectTask getTask() {
        return task;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "expert_id", nullable = false)
    public Expert getExpert() {
        return expert;
    }

    @Size(max = 255)
    @Column(name = "role")
    public String getRole() {
        return role;
    }

    @Column(name = "allocation_percentage", precision = 5, scale = 2)
    public BigDecimal getAllocationPercentage() {
        return allocationPercentage;
    }

    @Column(name = "start_date")
    public LocalDate getStartDate() {
        return startDate;
    }

    @Column(name = "end_date")
    public LocalDate getEndDate() {
        return endDate;
    }

    @Size(max = 50)
    @Column(name = "responsibility_level", length = 50)
    public String getResponsibilityLevel() {
        return responsibilityLevel;
    }

    @Column(name = "notes", length = Integer.MAX_VALUE)
    public String getNotes() {
        return notes;
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

}
