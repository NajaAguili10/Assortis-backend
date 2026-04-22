package com.backend.assorttis.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
@Table(name = "expert_skills", schema = "public")
public class ExpertSkill {
    private ExpertSkillId id;

    private Expert expert;

    private Skill skill;

    private String level;

    @EmbeddedId
    public ExpertSkillId getId() {
        return id;
    }

    @MapsId("expertId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "expert_id", nullable = false)
    public Expert getExpert() {
        return expert;
    }

    @MapsId("skillId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "skill_id", nullable = false)
    public Skill getSkill() {
        return skill;
    }

    @Size(max = 50)
    @Column(name = "level", length = 50)
    public String getLevel() {
        return level;
    }

}
