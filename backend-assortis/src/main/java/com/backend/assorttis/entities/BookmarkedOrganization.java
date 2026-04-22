package com.backend.assorttis.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "bookmarked_organizations")
public class BookmarkedOrganization {
    @EmbeddedId
    private BookmarkedOrganizationId id;

    @MapsId("organizationId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @MapsId("bookmarkedOrgId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bookmarked_org_id", nullable = false)
    private Organization bookmarkedOrg;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

}
