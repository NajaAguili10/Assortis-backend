package com.backend.assorttis.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Embeddable
public class TaskDependencyId implements Serializable {
    private static final long serialVersionUID = 2477653465048610344L;
    private Long taskId;

    private Long dependsOnTaskId;

    @NotNull
    @Column(name = "task_id", nullable = false)
    public Long getTaskId() {
        return taskId;
    }

    @NotNull
    @Column(name = "depends_on_task_id", nullable = false)
    public Long getDependsOnTaskId() {
        return dependsOnTaskId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        TaskDependencyId entity = (TaskDependencyId) o;
        return Objects.equals(this.dependsOnTaskId, entity.dependsOnTaskId) &&
                Objects.equals(this.taskId, entity.taskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dependsOnTaskId, taskId);
    }

}
