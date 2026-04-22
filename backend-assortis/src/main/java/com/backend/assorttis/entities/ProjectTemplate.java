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

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "project_templates", schema = "public", indexes = {
        @Index(name = "idx_project_templates_sector", columnList = "sector_id")
})
public class ProjectTemplate {
    private Long id;

    private String name;

    private String description;

    private Sector sector;

    private String type;

    private Integer estimatedDuration;

    private BigDecimal estimatedBudget;

    private Integer usageCount;

    private BigDecimal ratingAvg;

    private User createdBy;

    private Instant createdAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    @Column(name = "description", length = Integer.MAX_VALUE)
    public String getDescription() {
        return description;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "sector_id")
    public Sector getSector() {
        return sector;
    }

    @Size(max = 50)
    @Column(name = "type", length = 50)
    public String getType() {
        return type;
    }

    @Column(name = "estimated_duration")
    public Integer getEstimatedDuration() {
        return estimatedDuration;
    }

    @Column(name = "estimated_budget")
    public BigDecimal getEstimatedBudget() {
        return estimatedBudget;
    }

    @ColumnDefault("0")
    @Column(name = "usage_count")
    public Integer getUsageCount() {
        return usageCount;
    }

    @Column(name = "rating_avg", precision = 3, scale = 2)
    public BigDecimal getRatingAvg() {
        return ratingAvg;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "created_by")
    public User getCreatedBy() {
        return createdBy;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

}
