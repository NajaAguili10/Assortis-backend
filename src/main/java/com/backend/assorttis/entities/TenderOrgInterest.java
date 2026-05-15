package com.backend.assorttis.entities;

import jakarta.persistence.*;
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
import org.hibernate.mapping.Map;

import java.math.BigDecimal;
import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "tender_org_interest", schema = "public", indexes = {
        @Index(name = "idx_tender_org_interest_pipeline_stage", columnList = "pipeline_stage")
})
public class TenderOrgInterest {
    private TenderOrgInterestId id;

    private Tender tender;

    private Organization organization;

    private String role;

    private String message;

    private BigDecimal matchScore;

    private Boolean aiRecommendation;

    private String pipelineStage;

    private String priority;

    private Integer progressPercent;

    private Boolean wantsContact;

    private String roleSought;

    private Instant createdAt;

    private BigDecimal expectedValue;

    private BigDecimal probability;

    private Instant updatedAt;

    @EmbeddedId
    public TenderOrgInterestId getId() {
        return id;
    }

    @MapsId("tenderId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "tender_id", nullable = false)
    public Tender getTender() {
        return tender;
    }

    @MapsId("organizationId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "organization_id", nullable = false)
    public Organization getOrganization() {
        return organization;
    }

    @Size(max = 50)
    @Column(name = "role", length = 50)
    public String getRole() {
        return role;
    }

    @Column(name = "message", length = Integer.MAX_VALUE)
    public String getMessage() {
        return message;
    }

    @Column(name = "match_score", precision = 5, scale = 2)
    public BigDecimal getMatchScore() {
        return matchScore;
    }

    @Column(name = "ai_recommendation")
    public Boolean getAiRecommendation() {
        return aiRecommendation;
    }

    @Size(max = 50)
    @Column(name = "pipeline_stage", length = 50)
    public String getPipelineStage() {
        return pipelineStage;
    }

    @Size(max = 20)
    @Column(name = "priority", length = 20)
    public String getPriority() {
        return priority;
    }

    @ColumnDefault("0")
    @Column(name = "progress_percent")
    public Integer getProgressPercent() {
        return progressPercent;
    }

    @ColumnDefault("false")
    @Column(name = "wants_contact")
    public Boolean getWantsContact() {
        return wantsContact;
    }

    @Size(max = 50)
    @Column(name = "role_sought", length = 50)
    public String getRoleSought() {
        return roleSought;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

    @Column(name = "expected_value")
    public BigDecimal getExpectedValue() {
        return expectedValue;
    }

    @Column(name = "probability", precision = 5, scale = 2)
    public BigDecimal getProbability() {
        return probability;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    public Instant getUpdatedAt() {
        return updatedAt;
    }
    private String competitionLevel; // LOW, MEDIUM, HIGH
    private Integer estimatedEffort; // heures
    private BigDecimal potentialROI; // pourcentage

}
