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
public class TenderOrgInterestId implements Serializable {
    private static final long serialVersionUID = 268637934542430062L;
    private Long tenderId;

    private Long organizationId;

    @NotNull
    @Column(name = "tender_id", nullable = false)
    public Long getTenderId() {
        return tenderId;
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
        TenderOrgInterestId entity = (TenderOrgInterestId) o;
        return Objects.equals(this.organizationId, entity.organizationId) &&
                Objects.equals(this.tenderId, entity.tenderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizationId, tenderId);
    }

}
