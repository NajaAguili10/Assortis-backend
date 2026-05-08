package com.backend.assorttis.entities;

import jakarta.persistence.*;
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

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "ai_results", schema = "public")
public class AiResult {
    private Long id;

    private AiTask task;

    private String targetType;

    private Long targetId;

    private BigDecimal score;

    private Map<String, Object> result;

    private Instant createdAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "task_id")
    public AiTask getTask() {
        return task;
    }

    @Size(max = 50)
    @Column(name = "target_type", length = 50)
    public String getTargetType() {
        return targetType;
    }

    @Column(name = "target_id")
    public Long getTargetId() {
        return targetId;
    }

    @Column(name = "score", precision = 5, scale = 2)
    public BigDecimal getScore() {
        return score;
    }

    @Column(name = "result")
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getResult() {
        return result;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

}
