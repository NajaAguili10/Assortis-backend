package com.backend.assorttis.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "expert_fee_stats")
public class ExpertFeeStat {
    @Id
    @ColumnDefault("nextval('expert_fee_stats_id_seq')")
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sector_id")
    private Sector sector;

    @Size(max = 50)
    @Column(name = "seniority_level", length = 50)
    private String seniorityLevel;

    @Column(name = "daily_rate_min")
    private BigDecimal dailyRateMin;

    @Column(name = "daily_rate_max")
    private BigDecimal dailyRateMax;

    @Size(max = 10)
    @Column(name = "currency", length = 10)
    private String currency;

    @Column(name = "sample_size")
    private Integer sampleSize;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;

}
