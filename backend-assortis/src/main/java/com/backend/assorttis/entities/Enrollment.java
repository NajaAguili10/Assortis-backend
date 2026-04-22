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
@Table(name = "enrollments", schema = "public", indexes = {
        @Index(name = "idx_enrollments_user", columnList = "user_id"),
        @Index(name = "idx_enrollments_course", columnList = "course_id")
}, uniqueConstraints = {
        @UniqueConstraint(name = "enrollments_user_id_course_id_key", columnNames = { "user_id", "course_id" })
})
public class Enrollment {
    private Long id;

    private User user;

    private Cours course;

    private Instant enrolledAt;

    private Integer progressPercent;

    private Instant lastAccessedAt;

    private LocalDate deadline;

    private String status;

    private Instant completedAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    public User getUser() {
        return user;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "course_id", nullable = false)
    public Cours getCourse() {
        return course;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "enrolled_at")
    public Instant getEnrolledAt() {
        return enrolledAt;
    }

    @ColumnDefault("0")
    @Column(name = "progress_percent")
    public Integer getProgressPercent() {
        return progressPercent;
    }

    @Column(name = "last_accessed_at")
    public Instant getLastAccessedAt() {
        return lastAccessedAt;
    }

    @Column(name = "deadline")
    public LocalDate getDeadline() {
        return deadline;
    }

    @Size(max = 50)
    @ColumnDefault("'in_progress'")
    @Column(name = "status", length = 50)
    public String getStatus() {
        return status;
    }

    @Column(name = "completed_at")
    public Instant getCompletedAt() {
        return completedAt;
    }

}
