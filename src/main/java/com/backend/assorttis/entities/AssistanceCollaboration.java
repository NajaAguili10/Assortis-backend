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
@Table(name = "assistance_collaborations", schema = "public", indexes = {
        @Index(name = "idx_assistance_collaborations_request", columnList = "request_id"),
        @Index(name = "idx_assistance_collaborations_expert", columnList = "expert_id")
}, uniqueConstraints = {
        @UniqueConstraint(name = "assistance_collaborations_request_id_expert_id_key", columnNames = { "request_id",
                "expert_id" })
})
public class AssistanceCollaboration {
    private Long id;

    private AssistanceRequest request;

    private Expert expert;

    private String status;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer progress;

    private Map<String, Object> deliverables;

    private BigDecimal budgetTotal;

    private BigDecimal budgetSpent;

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
    @JoinColumn(name = "request_id", nullable = false)
    public AssistanceRequest getRequest() {
        return request;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "expert_id", nullable = false)
    public Expert getExpert() {
        return expert;
    }

    @Size(max = 20)
    @ColumnDefault("'PENDING'")
    @Column(name = "status", length = 20)
    public String getStatus() {
        return status;
    }

    @Column(name = "start_date")
    public LocalDate getStartDate() {
        return startDate;
    }

    @Column(name = "end_date")
    public LocalDate getEndDate() {
        return endDate;
    }

    @ColumnDefault("0")
    @Column(name = "progress")
    public Integer getProgress() {
        return progress;
    }

    @Column(name = "deliverables")
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getDeliverables() {
        return deliverables;
    }

    @Column(name = "budget_total")
    public BigDecimal getBudgetTotal() {
        return budgetTotal;
    }

    @Column(name = "budget_spent")
    public BigDecimal getBudgetSpent() {
        return budgetSpent;
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
