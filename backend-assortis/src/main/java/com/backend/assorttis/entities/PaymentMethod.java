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

import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "payment_methods", schema = "public", indexes = {
        @Index(name = "idx_payment_methods_owner", columnList = "owner_type, owner_id")
})
public class PaymentMethod {
    private Long id;

    private String ownerType;

    private Long ownerId;

    private String methodType;

    private String provider;

    private String last4;

    private Integer expiryMonth;

    private Integer expiryYear;

    private String holderName;

    private Boolean isDefault;

    private String token;

    private Instant createdAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Size(max = 20)
    @NotNull
    @Column(name = "owner_type", nullable = false, length = 20)
    public String getOwnerType() {
        return ownerType;
    }

    @NotNull
    @Column(name = "owner_id", nullable = false)
    public Long getOwnerId() {
        return ownerId;
    }

    @Size(max = 50)
    @NotNull
    @Column(name = "method_type", nullable = false, length = 50)
    public String getMethodType() {
        return methodType;
    }

    @Size(max = 50)
    @Column(name = "provider", length = 50)
    public String getProvider() {
        return provider;
    }

    @Size(max = 4)
    @Column(name = "last4", length = 4)
    public String getLast4() {
        return last4;
    }

    @Column(name = "expiry_month")
    public Integer getExpiryMonth() {
        return expiryMonth;
    }

    @Column(name = "expiry_year")
    public Integer getExpiryYear() {
        return expiryYear;
    }

    @Size(max = 255)
    @Column(name = "holder_name")
    public String getHolderName() {
        return holderName;
    }

    @ColumnDefault("false")
    @Column(name = "is_default")
    public Boolean getIsDefault() {
        return isDefault;
    }

    @Size(max = 255)
    @Column(name = "token")
    public String getToken() {
        return token;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

}
