package com.backend.assorttis.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

    @Getter
    @Setter
    @Entity
    @Table(name = "Expert_subscription_sectors")
    public class ExpertSubscriptionSector {
        @EmbeddedId
        private ExpertSubscriptionSectorId id;

        @MapsId("expertId")
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @OnDelete(action = OnDeleteAction.CASCADE)
        @JoinColumn(name = "expert_id", nullable = false)
        private Expert expert;

        @MapsId("sectorId")
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @OnDelete(action = OnDeleteAction.CASCADE)
        @JoinColumn(name = "sector_id", nullable = false)
        private Sector sector;



}
