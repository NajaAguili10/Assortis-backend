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
@Table(name = "external_links", schema = "public", indexes = {
        @Index(name = "idx_links_entity", columnList = "entity_type, entity_id")
})
public class ExternalLink {
    private Long id;

    private String entityType;

    private Long entityId;

    private String name;

    private String url;

    private Instant createdAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
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

    @Size(max = 255)
    @Column(name = "name")
    public String getName() {
        return name;
    }

    @NotNull
    @Column(name = "url", nullable = false, length = Integer.MAX_VALUE)
    public String getUrl() {
        return url;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

}
