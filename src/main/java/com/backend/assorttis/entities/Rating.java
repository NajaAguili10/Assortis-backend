package com.backend.assorttis.entities;

import jakarta.persistence.*;
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
@Table(name = "ratings", schema = "public")
public class Rating {
    private Long id;

    private String entityType;

    private Long entityId;

    private Integer score;

    private User reviewer;

    private String comment;

    private Boolean visibility;

    private Instant createdAt;

    private Instant updatedAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Size(max = 50)
    @Column(name = "entity_type", length = 50)
    public String getEntityType() {
        return entityType;
    }

    @Column(name = "entity_id")
    public Long getEntityId() {
        return entityId;
    }

    @Column(name = "score")
    public Integer getScore() {
        return score;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "reviewer_id")
    public User getReviewer() {
        return reviewer;
    }

    @Column(name = "comment", length = Integer.MAX_VALUE)
    public String getComment() {
        return comment;
    }

    @ColumnDefault("true")
    @Column(name = "visibility")
    public Boolean getVisibility() {
        return visibility;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

    @Column(name = "updated_at")
    public Instant getUpdatedAt() {
        return updatedAt;
    }

}
