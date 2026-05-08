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
@Table(name = "expert_experience_subsectors", schema = "public")
public class ExpertExperienceSubsector {
    private ExpertExperienceSubsectorId id;

    private ExpertExperience experience;

    private Subsector subsector;

    @EmbeddedId
    public ExpertExperienceSubsectorId getId() {
        return id;
    }

    @MapsId("experienceId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "experience_id", nullable = false)
    public ExpertExperience getExperience() {
        return experience;
    }

    @MapsId("subsectorId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "subsector_id", nullable = false)
    public Subsector getSubsector() {
        return subsector;
    }

}
