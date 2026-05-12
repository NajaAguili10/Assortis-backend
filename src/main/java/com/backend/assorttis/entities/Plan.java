package com.backend.assorttis.entities;

import com.backend.assorttis.entities.enums.UserType;
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
        @UniqueConstraint(name = "plans_code_key", columnNames = {"code"})
})
public class Plan {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 50)
    @Column(name = "code", length = 50)
    private String code;

    @Size(max = 100)
    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "features")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> features;

    @Column(name = "price")
    private BigDecimal price;

    @Size(max = 10)
    @Column(name = "currency", length = 10)
    private String currency;

    @Size(max = 20)
    @Column(name = "billing_cycle", length = 20)
    private String billingCycle;

    @ColumnDefault("0")
    @Column(name = "trial_days")
    private Integer trialDays;

    @ColumnDefault("true")
    @Column(name = "is_active")
    private Boolean isActive;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "name_jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> nameJsonb;

    @Column(name = "description_jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> descriptionJsonb;

    @Column(name = "price_monthly")
    private BigDecimal priceMonthly;

    @Column(name = "price_yearly")
    private BigDecimal priceYearly;

    @ColumnDefault("false")
    @Column(name = "highlighted")
    private Boolean highlighted;

    @Size(max = 100)
    @Column(name = "icon_name", length = 100)
    private String iconName;

    @Size(max = 100)
    @Column(name = "color_gradient", length = 100)
    private String colorGradient;

    @ColumnDefault("0")
    @Column(name = "display_order")
    private Integer displayOrder;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;

    // Nouveau champ : type d'utilisateur (enum)
    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", length = 20)
    private UserType userType;


}
