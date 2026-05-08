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
public class TenderCountryId implements Serializable {
    private static final long serialVersionUID = -4495454081098662609L;
    private Long tenderId;

    private Long countryId;

    @NotNull
    @Column(name = "tender_id", nullable = false)
    public Long getTenderId() {
        return tenderId;
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
        TenderCountryId entity = (TenderCountryId) o;
        return Objects.equals(this.tenderId, entity.tenderId) &&
                Objects.equals(this.countryId, entity.countryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tenderId, countryId);
    }

}
