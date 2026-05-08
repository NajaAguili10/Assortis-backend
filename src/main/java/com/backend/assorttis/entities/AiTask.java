package com.backend.assorttis.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "ai_tasks", schema = "public")
public class AiTask {
    private Long id;

    private String taskType;

    private String sourceType;

    private Long sourceId;

    private String status;

    private Integer priority;

    private String resultSummary;

    private Instant createdAt;

    private Instant startedAt;

    private Instant finishedAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Size(max = 50)
    @Column(name = "task_type", length = 50)
    public String getTaskType() {
        return taskType;
    }

    @Size(max = 50)
    @Column(name = "source_type", length = 50)
    public String getSourceType() {
        return sourceType;
    }

    @Column(name = "source_id")
    public Long getSourceId() {
        return sourceId;
    }

    @Size(max = 50)
    @Column(name = "status", length = 50)
    public String getStatus() {
        return status;
    }

    @ColumnDefault("0")
    @Column(name = "priority")
    public Integer getPriority() {
        return priority;
    }

    @Column(name = "result_summary", length = Integer.MAX_VALUE)
    public String getResultSummary() {
        return resultSummary;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

    @Column(name = "started_at")
    public Instant getStartedAt() {
        return startedAt;
    }

    @Column(name = "finished_at")
    public Instant getFinishedAt() {
        return finishedAt;
    }

}
