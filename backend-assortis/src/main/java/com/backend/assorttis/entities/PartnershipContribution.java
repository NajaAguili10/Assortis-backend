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
@Table(name = "partnership_contributions", schema = "public", indexes = {
        @Index(name = "idx_partnership_contributions_partnership", columnList = "partnership_id")
})
public class PartnershipContribution {
    private Long id;

    private Partnership partnership;

    private String contributionType;

    private BigDecimal amount;

    private String currency;

    private String description;

    private Instant createdAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "partnership_id", nullable = false)
    public Partnership getPartnership() {
        return partnership;
    }

    @Size(max = 100)
    @Column(name = "contribution_type", length = 100)
    public String getContributionType() {
        return contributionType;
    }

    @Column(name = "amount")
    public BigDecimal getAmount() {
        return amount;
    }

    @Size(max = 10)
    @Column(name = "currency", length = 10)
    public String getCurrency() {
        return currency;
    }

    @Column(name = "description", length = Integer.MAX_VALUE)
    public String getDescription() {
        return description;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

}
