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

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "proposal_status_history", schema = "public", indexes = {
        @Index(name = "idx_proposal_history", columnList = "proposal_id")
})
public class ProposalStatusHistory {
    private Long id;

    private Proposal proposal;

    private String status;

    private Instant changedAt;

    private Long changedBy;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "proposal_id")
    public Proposal getProposal() {
        return proposal;
    }

    @Size(max = 50)
    @Column(name = "status", length = 50)
    public String getStatus() {
        return status;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "changed_at")
    public Instant getChangedAt() {
        return changedAt;
    }

    @Column(name = "changed_by")
    public Long getChangedBy() {
        return changedBy;
    }

}
