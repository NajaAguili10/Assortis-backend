package com.backend.assorttis.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "scheduled_notification_jobs")
public class ScheduledNotificationJob {
    @Id
    @ColumnDefault("nextval('scheduled_notification_jobs_id_seq')")
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 100)
    @NotNull
    @Column(name = "job_name", nullable = false, length = 100)
    private String jobName;

    @Column(name = "last_run")
    private Instant lastRun;

    @Column(name = "next_run")
    private Instant nextRun;

    @ColumnDefault("true")
    @Column(name = "is_active")
    private Boolean isActive;

    @Size(max = 100)
    @Column(name = "cron_expression", length = 100)
    private String cronExpression;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

}
