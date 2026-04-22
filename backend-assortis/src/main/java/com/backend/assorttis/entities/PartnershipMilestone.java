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
@Table(name = "partnership_milestones", schema = "public", indexes = {
        @Index(name = "idx_partnership_milestones_partnership", columnList = "partnership_id")
})
public class PartnershipMilestone {
    private Long id;

    private Partnership partnership;

    private String title;

    private String description;

    private LocalDate dueDate;

    private LocalDate completedDate;

    private String status;

    private Instant createdAt;

    private Instant updatedAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "partnership_id", nullable = false)
    public Partnership getPartnership() {
        return partnership;
    }

    @Size(max = 255)
    @NotNull
    @Column(name = "title", nullable = false)
    public String getTitle() {
        return title;
    }

    @Column(name = "description", length = Integer.MAX_VALUE)
    public String getDescription() {
        return description;
    }

    @Column(name = "due_date")
    public LocalDate getDueDate() {
        return dueDate;
    }

    @Column(name = "completed_date")
    public LocalDate getCompletedDate() {
        return completedDate;
    }

    @Size(max = 20)
    @ColumnDefault("'pending'")
    @Column(name = "status", length = 20)
    public String getStatus() {
        return status;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    public Instant getUpdatedAt() {
        return updatedAt;
    }

}
