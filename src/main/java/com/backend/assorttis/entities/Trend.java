package com.backend.assorttis.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.math.BigDecimal;
import java.util.Map;

@Entity
@Table(name = "trends")
@Getter @Setter @NoArgsConstructor
public class Trend {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sector_id")
    private Sector sector;

    @ManyToOne
    @JoinColumn(name = "subsector_id")
    private Subsector subsector;

    @Column(name = "trend_name", nullable = false)
    private String trendName;

    @Column(length = 10)
    private String direction; // RISING, DECLINING, STABLE

    @Column(precision = 5, scale = 2)
    private BigDecimal strength;

    private String timeframe;

    private Integer volume;

    @Column(name = "growth_rate", precision = 5, scale = 2)
    private BigDecimal growthRate;

    @Column(name = "related_tenders")
    private Integer relatedTenders;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> keyFactors;
}
