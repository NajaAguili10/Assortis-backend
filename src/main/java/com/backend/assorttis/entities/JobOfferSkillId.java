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
public class JobOfferSkillId implements Serializable {
    private static final long serialVersionUID = 519364310230901190L;
    private Long jobOfferId;

    private Long skillId;

    @NotNull
    @Column(name = "job_offer_id", nullable = false)
    public Long getJobOfferId() {
        return jobOfferId;
    }

    @NotNull
    @Column(name = "skill_id", nullable = false)
    public Long getSkillId() {
        return skillId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        JobOfferSkillId entity = (JobOfferSkillId) o;
        return Objects.equals(this.skillId, entity.skillId) &&
                Objects.equals(this.jobOfferId, entity.jobOfferId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(skillId, jobOfferId);
    }

}
