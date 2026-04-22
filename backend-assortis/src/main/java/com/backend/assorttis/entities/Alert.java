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
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "alerts", schema = "public")
public class Alert {
    private Long id;

    private User user;

    private String name;

    private Map<String, Object> filters;

    private String frequency;

    private Instant lastSentAt;

    private Boolean isActive;

    private Instant createdAt;

    private Instant updatedAt;

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

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    @NotNull
    @Column(name = "filters", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getFilters() {
        return filters;
    }

    @Size(max = 20)
    @ColumnDefault("'daily'")
    @Column(name = "frequency", length = 20)
    public String getFrequency() {
        return frequency;
    }

    @Column(name = "last_sent_at")
    public Instant getLastSentAt() {
        return lastSentAt;
    }

    @ColumnDefault("true")
    @Column(name = "is_active")
    public Boolean getIsActive() {
        return isActive;
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
