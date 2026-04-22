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
public class OrganizationSectorId implements Serializable {
    private static final long serialVersionUID = 3595793749370015413L;
    private Long organizationId;

    private Long sectorId;

    @NotNull
    @Column(name = "organization_id", nullable = false)
    public Long getOrganizationId() {
        return organizationId;
    }

    @NotNull
    @Column(name = "sector_id", nullable = false)
    public Long getSectorId() {
        return sectorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        OrganizationSectorId entity = (OrganizationSectorId) o;
        return Objects.equals(this.organizationId, entity.organizationId) &&
                Objects.equals(this.sectorId, entity.sectorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizationId, sectorId);
    }

}
