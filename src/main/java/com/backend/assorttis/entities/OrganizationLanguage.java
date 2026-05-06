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
@Table(name = "organization_languages", schema = "public", indexes = {
        @Index(name = "idx_org_languages_org", columnList = "organization_id")
})
public class OrganizationLanguage {
    private OrganizationLanguageId id;

    private Organization organization;

    private Language languageCode;

    private String proficiency;

    private Instant createdAt;

    @EmbeddedId
    public OrganizationLanguageId getId() {
        return id;
    }

    @MapsId("organizationId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "organization_id", nullable = false)
    public Organization getOrganization() {
        return organization;
    }

    @MapsId("languageCode")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "language_code", nullable = false)
    public Language getLanguageCode() {
        return languageCode;
    }

    @Size(max = 20)
    @Column(name = "proficiency", length = 20)
    public String getProficiency() {
        return proficiency;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

}
