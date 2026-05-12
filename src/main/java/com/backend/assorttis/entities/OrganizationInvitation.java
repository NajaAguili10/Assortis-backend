package com.backend.assorttis.entities;

import com.backend.assorttis.entities.enums.OrganizationInvitationStatus;
import com.backend.assorttis.entities.enums.OrganizationInvitationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;

@Entity
@Table(name = "organization_invitations")
@Getter
@Setter
@NoArgsConstructor
public class OrganizationInvitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_organization_id", nullable = false)
    private Organization fromOrganization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_organization_id", nullable = false)
    private Organization toOrganization;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private OrganizationInvitationType type;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private OrganizationInvitationStatus status = OrganizationInvitationStatus.PENDING;

    @Column(name = "project_reference", length = 100)
    private String projectReference;

    @Column(name = "project_name", length = 255)
    private String projectName;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(name = "sent_at")
    private Instant sentAt = Instant.now();

    @Column(name = "expires_at")
    private Instant expiresAt;

    @Column(name = "responded_at")
    private Instant respondedAt;
}
