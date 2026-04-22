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

import java.math.BigDecimal;
import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "proposals", schema = "public")
public class Proposal {
    private Long id;

    private Tender tender;

    private TenderTor tor;

    private Consortium consortium;

    private Organization organization;

    private Expert expert;

    private TenderLot lot;

    private BigDecimal technicalScore;

    private BigDecimal financialScore;

    private BigDecimal totalScore;

    private String status;

    private String fileUrl;

    private Instant submittedAt;

    private BigDecimal proposedBudget;

    private String currency;

    private String feedback;

    private Instant updatedAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "tender_id")
    public Tender getTender() {
        return tender;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "tor_id")
    public TenderTor getTor() {
        return tor;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "consortium_id")
    public Consortium getConsortium() {
        return consortium;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "organization_id")
    public Organization getOrganization() {
        return organization;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "expert_id")
    public Expert getExpert() {
        return expert;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "lot_id")
    public TenderLot getLot() {
        return lot;
    }

    @Column(name = "technical_score", precision = 5, scale = 2)
    public BigDecimal getTechnicalScore() {
        return technicalScore;
    }

    @Column(name = "financial_score", precision = 5, scale = 2)
    public BigDecimal getFinancialScore() {
        return financialScore;
    }

    @Column(name = "total_score", precision = 5, scale = 2)
    public BigDecimal getTotalScore() {
        return totalScore;
    }

    @Size(max = 50)
    @Column(name = "status", length = 50)
    public String getStatus() {
        return status;
    }

    @Column(name = "file_url", length = Integer.MAX_VALUE)
    public String getFileUrl() {
        return fileUrl;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "submitted_at")
    public Instant getSubmittedAt() {
        return submittedAt;
    }

    @Column(name = "proposed_budget")
    public BigDecimal getProposedBudget() {
        return proposedBudget;
    }

    @Size(max = 10)
    @Column(name = "currency", length = 10)
    public String getCurrency() {
        return currency;
    }

    @Column(name = "feedback", length = Integer.MAX_VALUE)
    public String getFeedback() {
        return feedback;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    public Instant getUpdatedAt() {
        return updatedAt;
    }

}
