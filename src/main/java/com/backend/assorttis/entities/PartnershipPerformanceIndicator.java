package com.backend.assorttis.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@Table(name = "partnership_performance_indicators", schema = "public", indexes = {
        @Index(name = "idx_partnership_perf_indicator_partnership", columnList = "partnership_id")
})
public class PartnershipPerformanceIndicator {
    private Long id;

    private Partnership partnership;

    private String indicatorName;

    private String value;

    private String unit;

    private Instant createdAt;

    private Instant updatedAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "partnership_id", nullable = false)
    public Partnership getPartnership() {
        return partnership;
    }

    @Size(max = 255)
    @NotNull
    @Column(name = "indicator_name", nullable = false)
    public String getIndicatorName() {
        return indicatorName;
    }

    @Size(max = 255)
    @Column(name = "value")
    public String getValue() {
        return value;
    }

    @Size(max = 50)
    @Column(name = "unit", length = 50)
    public String getUnit() {
        return unit;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    public Instant getUpdatedAt() {
        return updatedAt;
    }

}
