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

import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "partnership_countries", schema = "public", indexes = {
        @Index(name = "idx_partnership_countries_partnership", columnList = "partnership_id"),
        @Index(name = "idx_partnership_countries_country", columnList = "country_id")
})
public class PartnershipCountry {
    private PartnershipCountryId id;

    private Partnership partnership;

    private Country country;

    private Instant createdAt;

    @EmbeddedId
    public PartnershipCountryId getId() {
        return id;
    }

    @MapsId("partnershipId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "partnership_id", nullable = false)
    public Partnership getPartnership() {
        return partnership;
    }

    @MapsId("countryId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "country_id", nullable = false)
    public Country getCountry() {
        return country;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

}
