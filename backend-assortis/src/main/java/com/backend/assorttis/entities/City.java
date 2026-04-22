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

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "cities", schema = "public")
public class City {
    private Long id;

    private Region region;

    private String name;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private Long population;

    private String timezone;

    private Boolean isCapital;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "region_id")
    public Region getRegion() {
        return region;
    }

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    @Column(name = "latitude", precision = 9, scale = 6)
    public BigDecimal getLatitude() {
        return latitude;
    }

    @Column(name = "longitude", precision = 9, scale = 6)
    public BigDecimal getLongitude() {
        return longitude;
    }

    @Column(name = "population")
    public Long getPopulation() {
        return population;
    }

    @Size(max = 50)
    @Column(name = "timezone", length = 50)
    public String getTimezone() {
        return timezone;
    }

    @ColumnDefault("false")
    @Column(name = "is_capital")
    public Boolean getIsCapital() {
        return isCapital;
    }

}
