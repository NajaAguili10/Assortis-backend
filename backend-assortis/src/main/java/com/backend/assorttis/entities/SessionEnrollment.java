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
@Table(name = "session_enrollments", schema = "public")
public class SessionEnrollment {
    private SessionEnrollmentId id;

    private LiveSession session;

    private User user;

    private Instant enrolledAt;

    private Boolean attended;

    @EmbeddedId
    public SessionEnrollmentId getId() {
        return id;
    }

    @MapsId("sessionId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "session_id", nullable = false)
    public LiveSession getSession() {
        return session;
    }

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    public User getUser() {
        return user;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "enrolled_at")
    public Instant getEnrolledAt() {
        return enrolledAt;
    }

    @ColumnDefault("false")
    @Column(name = "attended")
    public Boolean getAttended() {
        return attended;
    }

}
