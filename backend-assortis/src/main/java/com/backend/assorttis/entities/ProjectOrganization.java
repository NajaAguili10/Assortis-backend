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

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "project_organizations", schema = "public")
public class ProjectOrganization {
    private ProjectOrganizationId id;

    private Project project;

    private Organization organization;

    private String role;

    private Boolean isLead;

    @EmbeddedId
    public ProjectOrganizationId getId() {
        return id;
    }

    @MapsId("projectId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "project_id", nullable = false)
    public Project getProject() {
        return project;
    }

    @MapsId("organizationId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "organization_id", nullable = false)
    public Organization getOrganization() {
        return organization;
    }

    @Size(max = 50)
    @Column(name = "role", length = 50)
    public String getRole() {
        return role;
    }

    @ColumnDefault("false")
    @Column(name = "is_lead")
    public Boolean getIsLead() {
        return isLead;
    }

}
