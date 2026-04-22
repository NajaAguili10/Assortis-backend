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
@Table(name = "payment_history", schema = "public", indexes = {
        @Index(name = "idx_payment_history_invoice", columnList = "invoice_id"),
        @Index(name = "idx_payment_history_org", columnList = "organization_id"),
        @Index(name = "idx_payment_history_user", columnList = "user_id")
})
public class PaymentHistory {
    private Long id;

    private Payment payment;

    private Invoice invoice;

    private Organization organization;

    private User user;

    private BigDecimal amount;

    private String currency;

    private String status;

    private String transactionId;

    private Instant paymentDate;

    private Map<String, Object> details;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "payment_id")
    public Payment getPayment() {
        return payment;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "invoice_id")
    public Invoice getInvoice() {
        return invoice;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "organization_id")
    public Organization getOrganization() {
        return organization;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    @NotNull
    @Column(name = "amount", nullable = false)
    public BigDecimal getAmount() {
        return amount;
    }

    @Size(max = 10)
    @Column(name = "currency", length = 10)
    public String getCurrency() {
        return currency;
    }

    @Size(max = 50)
    @Column(name = "status", length = 50)
    public String getStatus() {
        return status;
    }

    @Size(max = 255)
    @Column(name = "transaction_id")
    public String getTransactionId() {
        return transactionId;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "payment_date")
    public Instant getPaymentDate() {
        return paymentDate;
    }

    @Column(name = "details")
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getDetails() {
        return details;
    }

}
