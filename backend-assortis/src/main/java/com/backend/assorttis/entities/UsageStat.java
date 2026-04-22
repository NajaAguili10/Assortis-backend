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
@Table(name = "usage_stats", schema = "public")
public class UsageStat {
    private Long id;

    private User user;

    private String feature;

    private Integer usageCount;

    private Instant lastUsedAt;

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

    @Size(max = 100)
    @Column(name = "feature", length = 100)
    public String getFeature() {
        return feature;
    }

    @ColumnDefault("0")
    @Column(name = "usage_count")
    public Integer getUsageCount() {
        return usageCount;
    }

    @Column(name = "last_used_at")
    public Instant getLastUsedAt() {
        return lastUsedAt;
    }

}
