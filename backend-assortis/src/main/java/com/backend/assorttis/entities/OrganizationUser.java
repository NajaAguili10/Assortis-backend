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
@Table(name = "organization_users", schema = "public", indexes = {
        @Index(name = "idx_organization_users_org_status", columnList = "organization_id, membership_status")
})
public class OrganizationUser {
    private OrganizationUserId id;

    private Organization organization;

    private User user;

    private String role;

    private Boolean isAdmin;

    private Instant joinedAt;

    private String department;

    private String membershipStatus;

    @EmbeddedId
    public OrganizationUserId getId() {
        return id;
    }

    @MapsId("organizationId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "organization_id", nullable = false)
    public Organization getOrganization() {
        return organization;
    }

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    public User getUser() {
        return user;
    }

    @Size(max = 50)
    @Column(name = "role", length = 50)
    public String getRole() {
        return role;
    }

    @ColumnDefault("false")
    @Column(name = "is_admin")
    public Boolean getIsAdmin() {
        return isAdmin;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "joined_at")
    public Instant getJoinedAt() {
        return joinedAt;
    }

    @Size(max = 255)
    @Column(name = "department")
    public String getDepartment() {
        return department;
    }

    @Size(max = 20)
    @ColumnDefault("'active'")
    @Column(name = "membership_status", length = 20)
    public String getMembershipStatus() {
        return membershipStatus;
    }

}
