package com.backend.assorttis.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "tender_evaluations", schema = "public", indexes = {
        @Index(name = "idx_tender_evaluations_tender", columnList = "tender_id"),
        @Index(name = "idx_tender_evaluations_proposal", columnList = "proposal_id")
})
public class TenderEvaluation {
    private Long id;

    private Tender tender;

    private Proposal proposal;

    private User evaluator;

    private BigDecimal technicalScore;

    private BigDecimal financialScore;

    private BigDecimal totalScore;

    private String comments;

    private Instant evaluatedAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "tender_id", nullable = false)
    public Tender getTender() {
        return tender;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "proposal_id", nullable = false)
    public Proposal getProposal() {
        return proposal;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "evaluator_id")
    public User getEvaluator() {
        return evaluator;
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

    @Column(name = "comments", length = Integer.MAX_VALUE)
    public String getComments() {
        return comments;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "evaluated_at")
    public Instant getEvaluatedAt() {
        return evaluatedAt;
    }

}
