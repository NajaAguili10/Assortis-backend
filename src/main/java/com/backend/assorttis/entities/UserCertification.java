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
@Table(name = "user_certifications", schema = "public")
public class UserCertification {
    private Long id;

    private User user;

    private Cours course;

    private String name;

    private String issuingOrganization;

    private LocalDate issueDate;

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
    @JoinColumn(name = "user_id", nullable = false)
    public User getUser() {
        return user;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "course_id")
    public Cours getCourse() {
        return course;
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

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

}
