package com.backend.assorttis.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "expert_credit_packs")
public class ExpertCreditPack {
    @Id
    @ColumnDefault("nextval('expert_credit_packs_id_seq')")
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @NotNull
    @Column(name = "credits_initial", nullable = false)
    private Integer creditsInitial;

    @NotNull
    @Column(name = "credits_remaining", nullable = false)
    private Integer creditsRemaining;

    @NotNull
    @Column(name = "purchase_date", nullable = false)
    private LocalDate purchaseDate;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Size(max = 20)
    @ColumnDefault("'active'")
    @Column(name = "status", length = 20)
    private String status;

}
