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
@Table(name = "assistance_resource_subsectors", schema = "public", indexes = {
        @Index(name = "idx_assistance_resource_subsectors_resource", columnList = "resource_id")
})
public class AssistanceResourceSubsector {
    private AssistanceResourceSubsectorId id;

    private AssistanceResource resource;

    private Subsector subsector;

    @EmbeddedId
    public AssistanceResourceSubsectorId getId() {
        return id;
    }

    @MapsId("resourceId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "resource_id", nullable = false)
    public AssistanceResource getResource() {
        return resource;
    }

    @MapsId("subsectorId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "subsector_id", nullable = false)
    public Subsector getSubsector() {
        return subsector;
    }

}
