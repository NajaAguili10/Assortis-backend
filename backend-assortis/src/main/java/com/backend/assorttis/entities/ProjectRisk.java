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

import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "project_risks", schema = "public", indexes = {
        @Index(name = "idx_project_risks_project", columnList = "project_id")
})
public class ProjectRisk {
    private Long id;

    private Project project;

    private String description;

    private String severity;

    private String probability;

    private String mitigationPlan;

    private String status;

    private User owner;

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
    @Column(name = "description", nullable = false, length = Integer.MAX_VALUE)
    public String getDescription() {
        return description;
    }

    @Size(max = 20)
    @Column(name = "severity", length = 20)
    public String getSeverity() {
        return severity;
    }

    @Size(max = 20)
    @Column(name = "probability", length = 20)
    public String getProbability() {
        return probability;
    }

    @Column(name = "mitigation_plan", length = Integer.MAX_VALUE)
    public String getMitigationPlan() {
        return mitigationPlan;
    }

    @Size(max = 50)
    @ColumnDefault("'identified'")
    @Column(name = "status", length = 50)
    public String getStatus() {
        return status;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "owner_id")
    public User getOwner() {
        return owner;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

    @Column(name = "updated_at")
    public Instant getUpdatedAt() {
        return updatedAt;
    }

}
