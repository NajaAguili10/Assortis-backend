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
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "contracts", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "contracts_proposal_id_unique", columnNames = { "proposal_id" })
})
public class Contract {
    private Long id;

    private Proposal proposal;

    private TenderLot lot;

    private LocalDate startDate;

    private LocalDate endDate;

    private BigDecimal amount;

    private String currency;

    private String status;

    private Instant signedAt;

    private User signedBy;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "proposal_id")
    public Proposal getProposal() {
        return proposal;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "lot_id")
    public TenderLot getLot() {
        return lot;
    }

    @Column(name = "start_date")
    public LocalDate getStartDate() {
        return startDate;
    }

    @Column(name = "end_date")
    public LocalDate getEndDate() {
        return endDate;
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

    @Size(max = 50)
    @Column(name = "status", length = 50)
    public String getStatus() {
        return status;
    }

    @Column(name = "signed_at")
    public Instant getSignedAt() {
        return signedAt;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "signed_by")
    public User getSignedBy() {
        return signedBy;
    }

}
