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
@Table(name = "favorites", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "favorites_user_id_entity_type_entity_id_key", columnNames = { "user_id",
                "entity_type", "entity_id" })
})
public class Favorite {
    private Long id;

    private User user;

    private String entityType;

    private Long entityId;

    private Instant createdAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    @Size(max = 50)
    @NotNull
    @Column(name = "entity_type", nullable = false, length = 50)
    public String getEntityType() {
        return entityType;
    }

    @NotNull
    @Column(name = "entity_id", nullable = false)
    public Long getEntityId() {
        return entityId;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

}
