package com.backend.assorttis.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Embeddable
public class SessionEnrollmentId implements Serializable {
    private static final long serialVersionUID = -3904680124253120009L;
    private Long sessionId;

    private Long userId;

    @NotNull
    @Column(name = "session_id", nullable = false)
    public Long getSessionId() {
        return sessionId;
    }

    @NotNull
    @Column(name = "user_id", nullable = false)
    public Long getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        SessionEnrollmentId entity = (SessionEnrollmentId) o;
        return Objects.equals(this.sessionId, entity.sessionId) &&
                Objects.equals(this.userId, entity.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, userId);
    }

}
