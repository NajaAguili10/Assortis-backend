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
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "organization_certifications", schema = "public", indexes = {
        @Index(name = "idx_org_certifications_org", columnList = "organization_id")
})
public class OrganizationCertification {
    private Long id;

    private Organization organization;

    private String certificationName;

    private String issuingOrganization;

    private LocalDate issuedDate;

    private LocalDate expiryDate;

    private String credentialId;

    private String credentialUrl;

    private Instant createdAt;

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

    @Size(max = 255)
    @NotNull
    @Column(name = "certification_name", nullable = false)
    public String getCertificationName() {
        return certificationName;
    }

    @Size(max = 255)
    @Column(name = "issuing_organization")
    public String getIssuingOrganization() {
        return issuingOrganization;
    }

    @Column(name = "issued_date")
    public LocalDate getIssuedDate() {
        return issuedDate;
    }

    @Column(name = "expiry_date")
    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    @Size(max = 100)
    @Column(name = "credential_id", length = 100)
    public String getCredentialId() {
        return credentialId;
    }

    @Column(name = "credential_url", length = Integer.MAX_VALUE)
    public String getCredentialUrl() {
        return credentialUrl;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

}
