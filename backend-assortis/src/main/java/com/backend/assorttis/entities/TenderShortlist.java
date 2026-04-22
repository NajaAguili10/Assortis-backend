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
@Table(name = "tender_shortlists", schema = "public", indexes = {
        @Index(name = "idx_shortlists_tender", columnList = "tender_id"),
        @Index(name = "idx_shortlists_org", columnList = "organization_id")
}, uniqueConstraints = {
        @UniqueConstraint(name = "tender_shortlists_tender_id_organization_id_key", columnNames = { "tender_id",
                "organization_id" })
})
public class TenderShortlist {
    private Long id;

    private Tender tender;

    private Organization organization;

    private BigDecimal score;

    private Integer rank;

    private String status;

    private Instant shortlistedAt;

    private String comments;

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
    @JoinColumn(name = "organization_id", nullable = false)
    public Organization getOrganization() {
        return organization;
    }

    @Column(name = "score", precision = 5, scale = 2)
    public BigDecimal getScore() {
        return score;
    }

    @Column(name = "rank")
    public Integer getRank() {
        return rank;
    }

    @Size(max = 50)
    @Column(name = "status", length = 50)
    public String getStatus() {
        return status;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "shortlisted_at")
    public Instant getShortlistedAt() {
        return shortlistedAt;
    }

    @Column(name = "comments", length = Integer.MAX_VALUE)
    public String getComments() {
        return comments;
    }

}
