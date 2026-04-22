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
public class OrganizationServiceId implements Serializable {
    private static final long serialVersionUID = -3388832341422832474L;
    private Long organizationId;

    private Long serviceId;

    @NotNull
    @Column(name = "organization_id", nullable = false)
    public Long getOrganizationId() {
        return organizationId;
    }

    @NotNull
    @Column(name = "service_id", nullable = false)
    public Long getServiceId() {
        return serviceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        OrganizationServiceId entity = (OrganizationServiceId) o;
        return Objects.equals(this.organizationId, entity.organizationId) &&
                Objects.equals(this.serviceId, entity.serviceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizationId, serviceId);
    }

}
