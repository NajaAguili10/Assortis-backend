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
public class PartnershipCountryId implements Serializable {
    private static final long serialVersionUID = 754068380705959476L;
    private Long partnershipId;

    private Long countryId;

    @NotNull
    @Column(name = "partnership_id", nullable = false)
    public Long getPartnershipId() {
        return partnershipId;
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
        PartnershipCountryId entity = (PartnershipCountryId) o;
        return Objects.equals(this.partnershipId, entity.partnershipId) &&
                Objects.equals(this.countryId, entity.countryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(partnershipId, countryId);
    }

}
