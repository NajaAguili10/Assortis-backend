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
@Table(name = "expert_sectors", schema = "public")
public class ExpertSector {
    private ExpertSectorId id;

    private Expert expert;

    private Sector sector;

    @EmbeddedId
    public ExpertSectorId getId() {
        return id;
    }

    @MapsId("expertId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "expert_id", nullable = false)
    public Expert getExpert() {
        return expert;
    }

    @MapsId("sectorId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "sector_id", nullable = false)
    public Sector getSector() {
        return sector;
    }

}
