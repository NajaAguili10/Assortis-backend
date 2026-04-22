package com.backend.assorttis.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "profile_change_requests")
public class ProfileChangeRequest {
    @Id
    @ColumnDefault("nextval('profile_change_requests_id_seq')")
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 50)
    @NotNull
    @Column(name = "entity_type", nullable = false, length = 50)
    private String entityType;

    @NotNull
    @Column(name = "entity_id", nullable = false)
    private Long entityId;

    @NotNull
    @Column(name = "proposed_changes", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> proposedChanges;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submitted_by")
    private User submittedBy;

    @Size(max = 20)
    @ColumnDefault("'pending'")
    @Column(name = "status", length = 20)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewed_by")
    private User reviewedBy;

    @Column(name = "reviewed_at")
    private Instant reviewedAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

}
