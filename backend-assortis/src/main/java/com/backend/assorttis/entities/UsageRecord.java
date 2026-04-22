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
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "usage_records", schema = "public", indexes = {
        @Index(name = "idx_usage_org", columnList = "organization_id")
})
public class UsageRecord {
    private Long id;

    private Organization organization;

    private Subscription subscription;

    private String featureCode;

    private Long used;

    private Long limitAmount;

    private LocalDate periodStart;

    private LocalDate periodEnd;

    private Instant updatedAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "organization_id")
    public Organization getOrganization() {
        return organization;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "subscription_id")
    public Subscription getSubscription() {
        return subscription;
    }

    @Size(max = 100)
    @NotNull
    @Column(name = "feature_code", nullable = false, length = 100)
    public String getFeatureCode() {
        return featureCode;
    }

    @ColumnDefault("0")
    @Column(name = "used")
    public Long getUsed() {
        return used;
    }

    @Column(name = "limit_amount")
    public Long getLimitAmount() {
        return limitAmount;
    }

    @NotNull
    @Column(name = "period_start", nullable = false)
    public LocalDate getPeriodStart() {
        return periodStart;
    }

    @NotNull
    @Column(name = "period_end", nullable = false)
    public LocalDate getPeriodEnd() {
        return periodEnd;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    public Instant getUpdatedAt() {
        return updatedAt;
    }

}
