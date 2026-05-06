package com.backend.assorttis.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "job_offer_skills", schema = "public")
public class JobOfferSkill {
    private JobOfferSkillId id;

    private JobOffer jobOffer;

    private Skill skill;

    @EmbeddedId
    public JobOfferSkillId getId() {
        return id;
    }

    @MapsId("jobOfferId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "job_offer_id", nullable = false)
    public JobOffer getJobOffer() {
        return jobOffer;
    }

    @MapsId("skillId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "skill_id", nullable = false)
    public Skill getSkill() {
        return skill;
    }

}
