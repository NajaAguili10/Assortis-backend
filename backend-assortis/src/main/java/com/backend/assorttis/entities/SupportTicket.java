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
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "support_tickets", schema = "public", indexes = {
        @Index(name = "idx_support_tickets_user", columnList = "user_id"),
        @Index(name = "idx_support_tickets_assigned", columnList = "assigned_to")
})
public class SupportTicket {
    private Long id;

    private User user;

    private Organization organization;

    private String subject;

    private String description;

    private String status;

    private String priority;

    private User assignedTo;

    private Instant createdAt;

    private Instant updatedAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "organization_id")
    public Organization getOrganization() {
        return organization;
    }

    @Size(max = 255)
    @NotNull
    @Column(name = "subject", nullable = false)
    public String getSubject() {
        return subject;
    }

    @Column(name = "description", length = Integer.MAX_VALUE)
    public String getDescription() {
        return description;
    }

    @Size(max = 50)
    @ColumnDefault("'open'")
    @Column(name = "status", length = 50)
    public String getStatus() {
        return status;
    }

    @Size(max = 20)
    @ColumnDefault("'normal'")
    @Column(name = "priority", length = 20)
    public String getPriority() {
        return priority;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "assigned_to")
    public User getAssignedTo() {
        return assignedTo;
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
