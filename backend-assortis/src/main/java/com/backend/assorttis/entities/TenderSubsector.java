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
@Table(name = "tender_subsectors", schema = "public")
public class TenderSubsector {
    private TenderSubsectorId id;

    private Tender tender;

    private Subsector subsector;

    @EmbeddedId
    public TenderSubsectorId getId() {
        return id;
    }

    @MapsId("tenderId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "tender_id", nullable = false)
    public Tender getTender() {
        return tender;
    }

    @MapsId("subsectorId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "subsector_id", nullable = false)
    public Subsector getSubsector() {
        return subsector;
    }

}
