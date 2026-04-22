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
@Table(name = "cv_views", schema = "public", indexes = {
        @Index(name = "idx_cv_views_user", columnList = "user_id"),
        @Index(name = "idx_cv_views_cv", columnList = "cv_id")
})
public class CvView {
    private Long id;

    private User user;

    private Cv cv;

    private Instant viewedAt;

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
    @JoinColumn(name = "cv_id", nullable = false)
    public Cv getCv() {
        return cv;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "viewed_at")
    public Instant getViewedAt() {
        return viewedAt;
    }

}
