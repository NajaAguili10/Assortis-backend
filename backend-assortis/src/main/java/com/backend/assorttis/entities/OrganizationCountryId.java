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
public class OrganizationCountryId implements Serializable {
    private static final long serialVersionUID = 630019951055304285L;
    private Long organizationId;

    private Long countryId;

    @NotNull
    @Column(name = "organization_id", nullable = false)
    public Long getOrganizationId() {
        return organizationId;
    }

    @NotNull
    @Column(name = "country_id", nullable = false)
    public Long getCountryId() {
        return countryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        OrganizationCountryId entity = (OrganizationCountryId) o;
        return Objects.equals(this.organizationId, entity.organizationId) &&
                Objects.equals(this.countryId, entity.countryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizationId, countryId);
    }

}
