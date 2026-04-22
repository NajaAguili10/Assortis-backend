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
import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "courses", schema = "public", indexes = {
        @Index(name = "idx_courses_certification_available", columnList = "certification_available")
})
public class Cours {
    private Long id;

    private String title;

    private String description;

    private Expert expert;

    private Integer durationHours;

    private String level;

    private BigDecimal price;

    private String currency;

    private Boolean isFree;

    private String status;

    private String thumbnailUrl;

    private Instant createdAt;

    private Boolean certificationAvailable;

    private BigDecimal certificationPrice;

    private String certificationTitle;

    private String certificationIssuer;

    private Integer certificationValidityMonths;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Size(max = 255)
    @NotNull
    @Column(name = "title", nullable = false)
    public String getTitle() {
        return title;
    }

    @Column(name = "description", length = Integer.MAX_VALUE)
    public String getDescription() {
        return description;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "expert_id")
    public Expert getExpert() {
        return expert;
    }

    @Column(name = "duration_hours")
    public Integer getDurationHours() {
        return durationHours;
    }

    @Size(max = 50)
    @Column(name = "level", length = 50)
    public String getLevel() {
        return level;
    }

    @Column(name = "price")
    public BigDecimal getPrice() {
        return price;
    }

    @Size(max = 10)
    @ColumnDefault("'USD'")
    @Column(name = "currency", length = 10)
    public String getCurrency() {
        return currency;
    }

    @ColumnDefault("false")
    @Column(name = "is_free")
    public Boolean getIsFree() {
        return isFree;
    }

    @Size(max = 50)
    @ColumnDefault("'published'")
    @Column(name = "status", length = 50)
    public String getStatus() {
        return status;
    }

    @Column(name = "thumbnail_url", length = Integer.MAX_VALUE)
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

    @ColumnDefault("false")
    @Column(name = "certification_available")
    public Boolean getCertificationAvailable() {
        return certificationAvailable;
    }

    @Column(name = "certification_price")
    public BigDecimal getCertificationPrice() {
        return certificationPrice;
    }

    @Size(max = 255)
    @Column(name = "certification_title")
    public String getCertificationTitle() {
        return certificationTitle;
    }

    @Size(max = 255)
    @Column(name = "certification_issuer")
    public String getCertificationIssuer() {
        return certificationIssuer;
    }

    @Column(name = "certification_validity_months")
    public Integer getCertificationValidityMonths() {
        return certificationValidityMonths;
    }

}
