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
@Table(name = "task_dependencies", schema = "public", indexes = {
        @Index(name = "idx_task_dependencies_task", columnList = "task_id"),
        @Index(name = "idx_task_dependencies_depends", columnList = "depends_on_task_id")
})
public class TaskDependency {
    private TaskDependencyId id;

    private ProjectTask task;

    private ProjectTask dependsOnTask;

    private String dependencyType;

    private Instant createdAt;

    @EmbeddedId
    public TaskDependencyId getId() {
        return id;
    }

    @MapsId("taskId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "task_id", nullable = false)
    public ProjectTask getTask() {
        return task;
    }

    @MapsId("dependsOnTaskId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "depends_on_task_id", nullable = false)
    public ProjectTask getDependsOnTask() {
        return dependsOnTask;
    }

    @Size(max = 50)
    @ColumnDefault("'finish_to_start'")
    @Column(name = "dependency_type", length = 50)
    public String getDependencyType() {
        return dependencyType;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

}
