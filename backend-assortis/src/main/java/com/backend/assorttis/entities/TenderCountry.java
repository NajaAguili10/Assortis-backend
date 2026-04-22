package com.backend.assorttis.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "tender_countries", schema = "public", indexes = {
        @Index(name = "idx_tender_countries_tender", columnList = "tender_id"),
        @Index(name = "idx_tender_countries_country", columnList = "country_id")
})
public class TenderCountry {
    private TenderCountryId id;

    private Tender tender;

    private Country country;

    @EmbeddedId
    public TenderCountryId getId() {
        return id;
    }

    @MapsId("tenderId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "tender_id", nullable = false)
    public Tender getTender() {
        return tender;
    }

    @MapsId("countryId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "country_id", nullable = false)
    public Country getCountry() {
        return country;
    }

}
