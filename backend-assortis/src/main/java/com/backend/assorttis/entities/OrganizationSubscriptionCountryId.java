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
public class OrganizationSubscriptionCountryId implements Serializable {
    private static final long serialVersionUID = -1545874899341802268L;
    @NotNull
    @Column(name = "organization_id", nullable = false)
    private Long organizationId;

    @NotNull
    @Column(name = "country_id", nullable = false)
    private Long countryId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OrganizationSubscriptionCountryId entity = (OrganizationSubscriptionCountryId) o;
        return Objects.equals(this.organizationId, entity.organizationId) &&
                Objects.equals(this.countryId, entity.countryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizationId, countryId);
    }

}
