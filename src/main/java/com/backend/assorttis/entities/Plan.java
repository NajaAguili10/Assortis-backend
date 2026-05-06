package com.backend.assorttis.entities;

import jakarta.persistence.*;
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
@Table(name = "plans", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "plans_code_key", columnNames = { "code" })
})
public class Plan {
    private Long id;

    private String code;

    private String name;

    private Map<String, Object> features;

    private BigDecimal price;

    private String currency;

    private String billingCycle;

    private Integer trialDays;

    private Boolean isActive;

    private Instant createdAt;

    private Map<String, Object> nameJsonb;

    private Map<String, Object> descriptionJsonb;

    private BigDecimal priceMonthly;

    private BigDecimal priceYearly;

    private Boolean highlighted;

    private String iconName;

    private String colorGradient;

    private Integer displayOrder;

    private Instant updatedAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Size(max = 50)
    @Column(name = "code", length = 50)
    public String getCode() {
        return code;
    }

    @Size(max = 100)
    @Column(name = "name", length = 100)
    public String getName() {
        return name;
    }

    @Column(name = "features")
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getFeatures() {
        return features;
    }

    @Column(name = "price")
    public BigDecimal getPrice() {
        return price;
    }

    @Size(max = 10)
    @Column(name = "currency", length = 10)
    public String getCurrency() {
        return currency;
    }

    @Size(max = 20)
    @Column(name = "billing_cycle", length = 20)
    public String getBillingCycle() {
        return billingCycle;
    }

    @ColumnDefault("0")
    @Column(name = "trial_days")
    public Integer getTrialDays() {
        return trialDays;
    }

    @ColumnDefault("true")
    @Column(name = "is_active")
    public Boolean getIsActive() {
        return isActive;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

    @Column(name = "name_jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getNameJsonb() {
        return nameJsonb;
    }

    @Column(name = "description_jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getDescriptionJsonb() {
        return descriptionJsonb;
    }

    @Column(name = "price_monthly")
    public BigDecimal getPriceMonthly() {
        return priceMonthly;
    }

    @Column(name = "price_yearly")
    public BigDecimal getPriceYearly() {
        return priceYearly;
    }

    @ColumnDefault("false")
    @Column(name = "highlighted")
    public Boolean getHighlighted() {
        return highlighted;
    }

    @Size(max = 100)
    @Column(name = "icon_name", length = 100)
    public String getIconName() {
        return iconName;
    }

    @Size(max = 100)
    @Column(name = "color_gradient", length = 100)
    public String getColorGradient() {
        return colorGradient;
    }

    @ColumnDefault("0")
    @Column(name = "display_order")
    public Integer getDisplayOrder() {
        return displayOrder;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    public Instant getUpdatedAt() {
        return updatedAt;
    }

}
