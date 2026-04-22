package com.backend.assorttis.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "tender_eligibility_criteria", schema = "public", indexes = {
        @Index(name = "idx_tender_eligibility_criteria_tender", columnList = "tender_id")
})
public class TenderEligibilityCriterion {
    private Long id;

    private Tender tender;

    private String criterion;

    private Boolean isMandatory;

    private Instant createdAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "tender_id", nullable = false)
    public Tender getTender() {
        return tender;
    }

    @NotNull
    @Column(name = "criterion", nullable = false, length = Integer.MAX_VALUE)
    public String getCriterion() {
        return criterion;
    }

    @ColumnDefault("true")
    @Column(name = "is_mandatory")
    public Boolean getIsMandatory() {
        return isMandatory;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

}
