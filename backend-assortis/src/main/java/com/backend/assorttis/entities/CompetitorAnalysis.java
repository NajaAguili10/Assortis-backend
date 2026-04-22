package com.backend.assorttis.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "competitor_analysis")
public class CompetitorAnalysis {
    @EmbeddedId
    private CompetitorAnalysisId id;

    @MapsId("organizationId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @MapsId("competitorId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "competitor_id", nullable = false)
    private Organization competitor;

    @ColumnDefault("0")
    @Column(name = "lost_tenders_count")
    private Integer lostTendersCount;

    @Column(name = "last_encounter_date")
    private LocalDate lastEncounterDate;

}
