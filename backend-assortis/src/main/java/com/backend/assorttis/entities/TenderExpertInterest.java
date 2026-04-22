package com.backend.assorttis.entities;

import jakarta.persistence.*;
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
@Table(name = "tender_expert_interest", schema = "public")
public class TenderExpertInterest {
    private TenderExpertInterestId id;

    private Tender tender;

    private Expert expert;

    private BigDecimal matchScore;

    private Boolean availabilityConfirmed;

    private Boolean wantsContact;

    private Instant createdAt;

    @EmbeddedId
    public TenderExpertInterestId getId() {
        return id;
    }

    @MapsId("tenderId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "tender_id", nullable = false)
    public Tender getTender() {
        return tender;
    }

    @MapsId("expertId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "expert_id", nullable = false)
    public Expert getExpert() {
        return expert;
    }

    @Column(name = "match_score", precision = 5, scale = 2)
    public BigDecimal getMatchScore() {
        return matchScore;
    }

    @Column(name = "availability_confirmed")
    public Boolean getAvailabilityConfirmed() {
        return availabilityConfirmed;
    }

    @ColumnDefault("false")
    @Column(name = "wants_contact")
    public Boolean getWantsContact() {
        return wantsContact;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

}
