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
@Table(name = "project_team_assignments", schema = "public", indexes = {
        @Index(name = "idx_project_team_assignments_project", columnList = "project_id"),
        @Index(name = "idx_project_team_assignments_user", columnList = "user_id")
}, uniqueConstraints = {
        @UniqueConstraint(name = "project_team_assignments_project_id_user_id_key", columnNames = { "project_id",
                "user_id" })
})
public class ProjectTeamAssignment {
    private Long id;

    private Project project;

    private User user;

    private String role;

    private BigDecimal allocationPercentage;

    private LocalDate startDate;

    private LocalDate endDate;

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
    @JoinColumn(name = "project_id", nullable = false)
    public Project getProject() {
        return project;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    public User getUser() {
        return user;
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
