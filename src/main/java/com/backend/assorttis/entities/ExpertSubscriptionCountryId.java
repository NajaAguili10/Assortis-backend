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
public class ExpertSubscriptionCountryId implements Serializable {
    private static final long serialVersionUID = -1545874899341802268L;
    @NotNull
    @Column(name = "expert_id", nullable = false)
    private Long expertId;

    @NotNull
    @Column(name = "country_id", nullable = false)
    private Long countryId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ExpertSubscriptionCountryId entity = (ExpertSubscriptionCountryId) o;
        return Objects.equals(this.expertId, entity.expertId) &&
                Objects.equals(this.countryId, entity.countryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expertId, countryId);
    }

}
