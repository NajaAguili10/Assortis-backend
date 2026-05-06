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
@Table(name = "partnership_sectors", schema = "public", indexes = {
        @Index(name = "idx_partnership_sectors_partnership", columnList = "partnership_id"),
        @Index(name = "idx_partnership_sectors_sector", columnList = "sector_id")
})
public class PartnershipSector {
    private PartnershipSectorId id;

    private Partnership partnership;

    private Sector sector;

    private Instant createdAt;

    @EmbeddedId
    public PartnershipSectorId getId() {
        return id;
    }

    @MapsId("partnershipId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "partnership_id", nullable = false)
    public Partnership getPartnership() {
        return partnership;
    }

    @MapsId("sectorId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "sector_id", nullable = false)
    public Sector getSector() {
        return sector;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

}
