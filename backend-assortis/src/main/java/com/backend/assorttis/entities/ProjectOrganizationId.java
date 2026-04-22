package com.backend.assorttis.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Embeddable
public class ProjectOrganizationId implements Serializable {
    private static final long serialVersionUID = -615901719236598488L;
    private Long projectId;

    private Long organizationId;

    @NotNull
    @Column(name = "project_id", nullable = false)
    public Long getProjectId() {
        return projectId;
    }

    @NotNull
    @Column(name = "organization_id", nullable = false)
    public Long getOrganizationId() {
        return organizationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        ProjectOrganizationId entity = (ProjectOrganizationId) o;
        return Objects.equals(this.organizationId, entity.organizationId) &&
                Objects.equals(this.projectId, entity.projectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizationId, projectId);
    }

}
