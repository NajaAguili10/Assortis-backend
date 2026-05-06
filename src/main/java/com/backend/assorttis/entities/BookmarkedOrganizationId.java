package com.backend.assorttis.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class BookmarkedOrganizationId implements Serializable {
    private static final long serialVersionUID = -4117404628795025468L;
    @NotNull
    @Column(name = "organization_id", nullable = false)
    private Long organizationId;

    @NotNull
    @Column(name = "bookmarked_org_id", nullable = false)
    private Long bookmarkedOrgId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BookmarkedOrganizationId entity = (BookmarkedOrganizationId) o;
        return Objects.equals(this.organizationId, entity.organizationId) &&
                Objects.equals(this.bookmarkedOrgId, entity.bookmarkedOrgId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizationId, bookmarkedOrgId);
    }

}
