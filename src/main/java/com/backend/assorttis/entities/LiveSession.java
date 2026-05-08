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

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "live_sessions", schema = "public")
public class LiveSession {
    private Long id;

    private Cours course;

    private String title;

    private String description;

    private Instant startTime;

    private Instant endTime;

    private Integer durationMinutes;

    private Language language;

    private Integer maxParticipants;

    private Integer currentParticipants;

    private String status;

    private String recordingUrl;

    private Instant createdAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "course_id")
    public Cours getCourse() {
        return course;
    }

    @Size(max = 255)
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    @Column(name = "description", length = Integer.MAX_VALUE)
    public String getDescription() {
        return description;
    }

    @NotNull
    @Column(name = "start_time", nullable = false)
    public Instant getStartTime() {
        return startTime;
    }

    @NotNull
    @Column(name = "end_time", nullable = false)
    public Instant getEndTime() {
        return endTime;
    }

    @Column(name = "duration_minutes")
    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "language")
    public Language getLanguage() {
        return language;
    }

    @Column(name = "max_participants")
    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    @ColumnDefault("0")
    @Column(name = "current_participants")
    public Integer getCurrentParticipants() {
        return currentParticipants;
    }

    @Size(max = 50)
    @ColumnDefault("'scheduled'")
    @Column(name = "status", length = 50)
    public String getStatus() {
        return status;
    }

    @Column(name = "recording_url", length = Integer.MAX_VALUE)
    public String getRecordingUrl() {
        return recordingUrl;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

}
