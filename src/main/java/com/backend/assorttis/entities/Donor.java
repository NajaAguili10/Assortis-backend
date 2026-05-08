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
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "donors", schema = "public")
public class Donor {
    private Long id;

    private String name;

    private String shortName;

    private String type;

    private BigDecimal fundingVolume;

    private Map<String, Object> focusRegions;

    private Map<String, Object> focusSectors;

    private BigDecimal ratingAvg;

    private String logoUrl;

    private Country country;

    private City city;

    private String website;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private Instant createdAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    @Size(max = 100)
    @Column(name = "short_name", length = 100)
    public String getShortName() {
        return shortName;
    }

    @Size(max = 50)
    @Column(name = "type", length = 50)
    public String getType() {
        return type;
    }

    @Column(name = "funding_volume")
    public BigDecimal getFundingVolume() {
        return fundingVolume;
    }

    @Column(name = "focus_regions")
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getFocusRegions() {
        return focusRegions;
    }

    @Column(name = "focus_sectors")
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getFocusSectors() {
        return focusSectors;
    }

    @Column(name = "rating_avg", precision = 3, scale = 2)
    public BigDecimal getRatingAvg() {
        return ratingAvg;
    }

    @Column(name = "logo_url", length = Integer.MAX_VALUE)
    public String getLogoUrl() {
        return logoUrl;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "country_id")
    public Country getCountry() {
        return country;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "city_id")
    public City getCity() {
        return city;
    }

    @Size(max = 255)
    @Column(name = "website")
    public String getWebsite() {
        return website;
    }

    @Column(name = "latitude", precision = 9, scale = 6)
    public BigDecimal getLatitude() {
        return latitude;
    }

    @Column(name = "longitude", precision = 9, scale = 6)
    public BigDecimal getLongitude() {
        return longitude;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

}
