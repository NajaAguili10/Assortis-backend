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
@Table(name = "payments", schema = "public", indexes = {
        @Index(name = "idx_payments_invoice", columnList = "invoice_id")
})
public class Payment {
    private Long id;

    private Invoice invoice;

    private PaymentMethod paymentMethod;

    private BigDecimal amount;

    private String currency;

    private String status;

    private String transactionId;

    private Instant paidAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "invoice_id", nullable = false)
    public Invoice getInvoice() {
        return invoice;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "payment_method_id")
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
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
    @Column(name = "paid_at")
    public Instant getPaidAt() {
        return paidAt;
    }

}
