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
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "invoices", schema = "public", indexes = {
        @Index(name = "idx_invoices_organization", columnList = "organization_id"),
        @Index(name = "idx_invoices_user", columnList = "user_id")
}, uniqueConstraints = {
        @UniqueConstraint(name = "invoices_invoice_number_key", columnNames = { "invoice_number" })
})
public class Invoice {
    private Long id;

    private Organization organization;

    private User user;

    private Subscription subscription;

    private String invoiceNumber;

    private LocalDate issueDate;

    private LocalDate dueDate;

    private BigDecimal totalAmount;

    private String currency;

    private String status;

    private Instant paidAt;

    private String pdfUrl;

    private Instant createdAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "subscription_id")
    public Subscription getSubscription() {
        return subscription;
    }

    @Size(max = 100)
    @NotNull
    @Column(name = "invoice_number", nullable = false, length = 100)
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    @NotNull
    @Column(name = "issue_date", nullable = false)
    public LocalDate getIssueDate() {
        return issueDate;
    }

    @Column(name = "due_date")
    public LocalDate getDueDate() {
        return dueDate;
    }

    @NotNull
    @Column(name = "total_amount", nullable = false)
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    @Size(max = 10)
    @Column(name = "currency", length = 10)
    public String getCurrency() {
        return currency;
    }

    @Size(max = 50)
    @ColumnDefault("'pending'")
    @Column(name = "status", length = 50)
    public String getStatus() {
        return status;
    }

    @Column(name = "paid_at")
    public Instant getPaidAt() {
        return paidAt;
    }

    @Column(name = "pdf_url", length = Integer.MAX_VALUE)
    public String getPdfUrl() {
        return pdfUrl;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

}
