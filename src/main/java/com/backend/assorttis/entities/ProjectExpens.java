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
@Table(name = "project_expenses", schema = "public", indexes = {
        @Index(name = "idx_project_expenses_project", columnList = "project_id"),
        @Index(name = "idx_project_expenses_date", columnList = "expense_date")
})
public class ProjectExpens {
    private Long id;

    private Project project;

    private BigDecimal amount;

    private String currency;

    private String description;

    private LocalDate expenseDate;

    private String category;

    private Instant createdAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "project_id", nullable = false)
    public Project getProject() {
        return project;
    }

    @NotNull
    @Column(name = "amount", nullable = false)
    public BigDecimal getAmount() {
        return amount;
    }

    @Size(max = 10)
    @ColumnDefault("'USD'")
    @Column(name = "currency", length = 10)
    public String getCurrency() {
        return currency;
    }

    @Column(name = "description", length = Integer.MAX_VALUE)
    public String getDescription() {
        return description;
    }

    @NotNull
    @Column(name = "expense_date", nullable = false)
    public LocalDate getExpenseDate() {
        return expenseDate;
    }

    @Size(max = 100)
    @Column(name = "category", length = 100)
    public String getCategory() {
        return category;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

}
