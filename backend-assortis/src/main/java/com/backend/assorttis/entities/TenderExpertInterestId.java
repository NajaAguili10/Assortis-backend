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
public class TenderExpertInterestId implements Serializable {
    private static final long serialVersionUID = -2079705977581445929L;
    private Long tenderId;

    private Long expertId;

    @NotNull
    @Column(name = "tender_id", nullable = false)
    public Long getTenderId() {
        return tenderId;
    }

    @NotNull
    @Column(name = "expert_id", nullable = false)
    public Long getExpertId() {
        return expertId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        TenderExpertInterestId entity = (TenderExpertInterestId) o;
        return Objects.equals(this.expertId, entity.expertId) &&
                Objects.equals(this.tenderId, entity.tenderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expertId, tenderId);
    }

}
