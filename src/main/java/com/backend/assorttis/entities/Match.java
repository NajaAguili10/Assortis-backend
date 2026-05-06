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

import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "matches", schema = "public", indexes = {
        @Index(name = "idx_matches_entity1", columnList = "entity_type_1, entity_id_1"),
        @Index(name = "idx_matches_entity2", columnList = "entity_type_2, entity_id_2")
})
public class Match {
    private Long id;

    private String entityType1;

    private Long entityId1;

    private String entityType2;

    private Long entityId2;

    private String matchType;

    private Instant confirmedAt;

    private Instant createdAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Size(max = 50)
    @NotNull
    @Column(name = "entity_type_1", nullable = false, length = 50)
    public String getEntityType1() {
        return entityType1;
    }

    @NotNull
    @Column(name = "entity_id_1", nullable = false)
    public Long getEntityId1() {
        return entityId1;
    }

    @Size(max = 50)
    @NotNull
    @Column(name = "entity_type_2", nullable = false, length = 50)
    public String getEntityType2() {
        return entityType2;
    }

    @NotNull
    @Column(name = "entity_id_2", nullable = false)
    public Long getEntityId2() {
        return entityId2;
    }

    @Size(max = 50)
    @NotNull
    @Column(name = "match_type", nullable = false, length = 50)
    public String getMatchType() {
        return matchType;
    }

    @Column(name = "confirmed_at")
    public Instant getConfirmedAt() {
        return confirmedAt;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

}
