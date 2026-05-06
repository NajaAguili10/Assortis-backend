package com.backend.assorttis.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

    @Getter
    @Setter
    @Entity
    @Table(name = "Expert_subscription_countries")
    public class ExpertSubscriptionCountry {
        @EmbeddedId
        private ExpertSubscriptionCountryId id;

        @MapsId("expertId")
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @OnDelete(action = OnDeleteAction.CASCADE)
        @JoinColumn(name = "expert_id", nullable = false)
        private Expert expert;

        @MapsId("countryId")
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @OnDelete(action = OnDeleteAction.CASCADE)
        @JoinColumn(name = "country_id", nullable = false)
        private Country country;



}
