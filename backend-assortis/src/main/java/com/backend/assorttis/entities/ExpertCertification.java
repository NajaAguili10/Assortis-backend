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

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "expert_certifications", schema = "public")
public class ExpertCertification {
    private Long id;

    private Expert expert;

    private String name;

    private String issuingOrganization;

    private LocalDate issueDate;

    private LocalDate expiryDate;

    private String credentialId;

    private String credentialUrl;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "expert_id")
    public Expert getExpert() {
        return expert;
    }

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    @Size(max = 255)
    @Column(name = "issuing_organization")
    public String getIssuingOrganization() {
        return issuingOrganization;
    }

    @Column(name = "issue_date")
    public LocalDate getIssueDate() {
        return issueDate;
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

}
