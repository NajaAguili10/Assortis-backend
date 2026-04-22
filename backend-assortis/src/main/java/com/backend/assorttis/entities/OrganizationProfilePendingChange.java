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
@Table(name = "organization_profile_pending_changes", schema = "public", indexes = {
        @Index(name = "idx_org_pending_changes_org", columnList = "organization_id")
})
public class OrganizationProfilePendingChange {
    private Long id;

    private Organization organization;

    private Map<String, Object> changes;

    private Instant submittedAt;

    private User reviewedBy;

    private Instant reviewedAt;

    private String status;

    private String comments;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "organization_id", nullable = false)
    public Organization getOrganization() {
        return organization;
    }

    @NotNull
    @Column(name = "changes", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getChanges() {
        return changes;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "submitted_at")
    public Instant getSubmittedAt() {
        return submittedAt;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "reviewed_by")
    public User getReviewedBy() {
        return reviewedBy;
    }

    @Column(name = "reviewed_at")
    public Instant getReviewedAt() {
        return reviewedAt;
    }

    @Size(max = 20)
    @ColumnDefault("'pending'")
    @Column(name = "status", length = 20)
    public String getStatus() {
        return status;
    }

    @Column(name = "comments", length = Integer.MAX_VALUE)
    public String getComments() {
        return comments;
    }

}
