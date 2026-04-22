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

import java.time.Instant;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "expert_availability", schema = "public", indexes = {
        @Index(name = "idx_expert_availability_expert", columnList = "expert_id")
})
public class ExpertAvailability {
    private Long id;

    private Expert expert;

    private LocalDate startDate;

    private LocalDate endDate;

    private String status;

    private String notes;

    private Instant createdAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "expert_id", nullable = false)
    public Expert getExpert() {
        return expert;
    }

    @NotNull
    @Column(name = "start_date", nullable = false)
    public LocalDate getStartDate() {
        return startDate;
    }

    @NotNull
    @Column(name = "end_date", nullable = false)
    public LocalDate getEndDate() {
        return endDate;
    }

    @Size(max = 20)
    @ColumnDefault("'available'")
    @Column(name = "status", length = 20)
    public String getStatus() {
        return status;
    }

    @Column(name = "notes", length = Integer.MAX_VALUE)
    public String getNotes() {
        return notes;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

}
