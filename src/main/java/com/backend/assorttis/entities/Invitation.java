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

import java.math.BigDecimal;
import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "invitations", schema = "public", indexes = {
        @Index(name = "idx_invitations_expires_at", columnList = "expires_at"),
        @Index(name = "idx_invitations_inviter_org", columnList = "inviter_organization_id")
})
public class Invitation {
    private Long id;

    private Tender tender;

    private User inviter;

    private Expert invitee;

    private String invitationType;

    private String message;

    private String status;

    private String memberRole;

    private String memberDepartment;

    private String memberStatus;

    private Instant createdAt;

    private Instant respondedAt;

    private Instant expiresAt;

    private Project project;

    private Organization inviteeOrganization;

    private Organization inviterOrganization;

    private BigDecimal matchScore;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "tender_id")
    public Tender getTender() {
        return tender;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "inviter_id")
    public User getInviter() {
        return inviter;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "invitee_id")
    public Expert getInvitee() {
        return invitee;
    }

    @Size(max = 50)
    @NotNull
    @Column(name = "invitation_type", nullable = false, length = 50)
    public String getInvitationType() {
        return invitationType;
    }

    @Column(name = "message", length = Integer.MAX_VALUE)
    public String getMessage() {
        return message;
    }

    @Size(max = 50)
    @ColumnDefault("'PENDING'")
    @Column(name = "status", length = 50)
    public String getStatus() {
        return status;
    }

    @Size(max = 50)
    @Column(name = "member_role", length = 50)
    public String getMemberRole() {
        return memberRole;
    }

    @Size(max = 255)
    @Column(name = "member_department")
    public String getMemberDepartment() {
        return memberDepartment;
    }

    @Size(max = 20)
    @ColumnDefault("'pending'")
    @Column(name = "member_status", length = 20)
    public String getMemberStatus() {
        return memberStatus;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

    @Column(name = "responded_at")
    public Instant getRespondedAt() {
        return respondedAt;
    }

    @Column(name = "expires_at")
    public Instant getExpiresAt() {
        return expiresAt;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "project_id")
    public Project getProject() {
        return project;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "invitee_organization_id")
    public Organization getInviteeOrganization() {
        return inviteeOrganization;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "inviter_organization_id")
    public Organization getInviterOrganization() {
        return inviterOrganization;
    }

    @Column(name = "match_score", precision = 5, scale = 2)
    public BigDecimal getMatchScore() {
        return matchScore;
    }

}
