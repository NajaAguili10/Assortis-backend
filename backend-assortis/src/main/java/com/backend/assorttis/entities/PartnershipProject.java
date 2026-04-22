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
@Table(name = "partnership_projects", schema = "public")
public class PartnershipProject {
    private PartnershipProjectId id;

    private Partnership partnership;

    private Project project;

    @EmbeddedId
    public PartnershipProjectId getId() {
        return id;
    }

    @MapsId("partnershipId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "partnership_id", nullable = false)
    public Partnership getPartnership() {
        return partnership;
    }

    @MapsId("projectId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "project_id", nullable = false)
    public Project getProject() {
        return project;
    }

}
