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
@Table(name = "entity_workflow_status", schema = "public")
public class EntityWorkflowStatus {
    private Long id;

    private String entityType;

    private Long entityId;

    private WorkflowStep workflowStep;

    private String status;

    private Instant updatedAt;

    private User updatedBy;

    private String comment;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Size(max = 50)
    @Column(name = "entity_type", length = 50)
    public String getEntityType() {
        return entityType;
    }

    @Column(name = "entity_id")
    public Long getEntityId() {
        return entityId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "workflow_step_id")
    public WorkflowStep getWorkflowStep() {
        return workflowStep;
    }

    @Size(max = 50)
    @Column(name = "status", length = 50)
    public String getStatus() {
        return status;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    public Instant getUpdatedAt() {
        return updatedAt;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "updated_by")
    public User getUpdatedBy() {
        return updatedBy;
    }

    @Column(name = "comment", length = Integer.MAX_VALUE)
    public String getComment() {
        return comment;
    }

}
