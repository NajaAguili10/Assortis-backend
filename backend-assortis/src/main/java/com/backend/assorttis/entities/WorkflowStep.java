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
@Table(name = "workflow_steps", schema = "public")
public class WorkflowStep {
    private Long id;

    private Workflow workflow;

    private Integer stepOrder;

    private String code;

    private String label;

    private String description;

    private Boolean required;

    private Instant createdAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "workflow_id")
    public Workflow getWorkflow() {
        return workflow;
    }

    @Column(name = "step_order")
    public Integer getStepOrder() {
        return stepOrder;
    }

    @Size(max = 50)
    @Column(name = "code", length = 50)
    public String getCode() {
        return code;
    }

    @Size(max = 100)
    @Column(name = "label", length = 100)
    public String getLabel() {
        return label;
    }

    @Column(name = "description", length = Integer.MAX_VALUE)
    public String getDescription() {
        return description;
    }

    @ColumnDefault("true")
    @Column(name = "required")
    public Boolean getRequired() {
        return required;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

}
