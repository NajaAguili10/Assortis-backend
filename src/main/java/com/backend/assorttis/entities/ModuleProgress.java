package com.backend.assorttis.entities;

import jakarta.persistence.*;
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
@Table(name = "module_progress", schema = "public")
public class ModuleProgress {
    private ModuleProgressId id;

    private Enrollment enrollment;

    private CourseModule module;

    private Boolean completed;

    private Instant lastAccessedAt;

    @EmbeddedId
    public ModuleProgressId getId() {
        return id;
    }

    @MapsId("enrollmentId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "enrollment_id", nullable = false)
    public Enrollment getEnrollment() {
        return enrollment;
    }

    @MapsId("moduleId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "module_id", nullable = false)
    public CourseModule getModule() {
        return module;
    }

    @ColumnDefault("false")
    @Column(name = "completed")
    public Boolean getCompleted() {
        return completed;
    }

    @Column(name = "last_accessed_at")
    public Instant getLastAccessedAt() {
        return lastAccessedAt;
    }

}
