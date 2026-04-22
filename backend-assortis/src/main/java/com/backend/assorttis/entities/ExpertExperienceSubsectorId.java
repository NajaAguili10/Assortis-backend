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
public class ExpertExperienceSubsectorId implements Serializable {
    private static final long serialVersionUID = 8133026092470780574L;
    private Long experienceId;

    private Long subsectorId;

    @NotNull
    @Column(name = "experience_id", nullable = false)
    public Long getExperienceId() {
        return experienceId;
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
        ExpertExperienceSubsectorId entity = (ExpertExperienceSubsectorId) o;
        return Objects.equals(this.experienceId, entity.experienceId) &&
                Objects.equals(this.subsectorId, entity.subsectorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(experienceId, subsectorId);
    }

}
