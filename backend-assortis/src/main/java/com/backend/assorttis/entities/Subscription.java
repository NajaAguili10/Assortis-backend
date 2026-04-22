package com.backend.assorttis.entities;

import jakarta.persistence.*;
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
@Table(name = "subscriptions", schema = "public")
public class Subscription {
    private Long id;

    private Organization organization;

    private Plan plan;

    private LocalDate startDate;

    private LocalDate endDate;

    private String status;

    private Boolean autoRenew;

    private Instant trialEndsAt;

    private Instant cancelledAt;

    private LocalDate nextBillingDate;

    private Instant createdAt;

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
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "plan_id")
    public Plan getPlan() {
        return plan;
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
    @Column(name = "status", length = 50)
    public String getStatus() {
        return status;
    }

    @ColumnDefault("true")
    @Column(name = "auto_renew")
    public Boolean getAutoRenew() {
        return autoRenew;
    }

    @Column(name = "trial_ends_at")
    public Instant getTrialEndsAt() {
        return trialEndsAt;
    }

    @Column(name = "cancelled_at")
    public Instant getCancelledAt() {
        return cancelledAt;
    }

    @Column(name = "next_billing_date")
    public LocalDate getNextBillingDate() {
        return nextBillingDate;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

}
