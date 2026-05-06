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
@Table(name = "promo_codes", schema = "public", indexes = {
        @Index(name = "idx_promo_codes_code", columnList = "code"),
        @Index(name = "idx_promo_codes_validity", columnList = "valid_from, valid_until")
}, uniqueConstraints = {
        @UniqueConstraint(name = "promo_codes_code_key", columnNames = { "code" })
})
public class PromoCode {
    private Long id;

    private String code;

    private BigDecimal discountPercent;

    private Instant validFrom;

    private Instant validUntil;

    private Integer maxUses;

    private Integer usedCount;

    private Boolean isActive;

    private Map<String, Object> organizationData;

    private Instant createdAt;

    private Instant updatedAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Size(max = 50)
    @NotNull
    @Column(name = "code", nullable = false, length = 50)
    public String getCode() {
        return code;
    }

    @NotNull
    @Column(name = "discount_percent", nullable = false, precision = 5, scale = 2)
    public BigDecimal getDiscountPercent() {
        return discountPercent;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "valid_from")
    public Instant getValidFrom() {
        return validFrom;
    }

    @Column(name = "valid_until")
    public Instant getValidUntil() {
        return validUntil;
    }

    @Column(name = "max_uses")
    public Integer getMaxUses() {
        return maxUses;
    }

    @ColumnDefault("0")
    @Column(name = "used_count")
    public Integer getUsedCount() {
        return usedCount;
    }

    @ColumnDefault("true")
    @Column(name = "is_active")
    public Boolean getIsActive() {
        return isActive;
    }

    @Column(name = "organization_data")
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getOrganizationData() {
        return organizationData;
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
