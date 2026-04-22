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
public class TenderSubsectorId implements Serializable {
    private static final long serialVersionUID = 8205158772936316751L;
    private Long tenderId;

    private Long subsectorId;

    @NotNull
    @Column(name = "tender_id", nullable = false)
    public Long getTenderId() {
        return tenderId;
    }

    @NotNull
    @Column(name = "subsector_id", nullable = false)
    public Long getSubsectorId() {
        return subsectorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        TenderSubsectorId entity = (TenderSubsectorId) o;
        return Objects.equals(this.subsectorId, entity.subsectorId) &&
                Objects.equals(this.tenderId, entity.tenderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subsectorId, tenderId);
    }

}
