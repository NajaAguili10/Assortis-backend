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
@Table(name = "assistance_request_subsectors", schema = "public", indexes = {
        @Index(name = "idx_assistance_request_subsectors_request", columnList = "request_id")
})
public class AssistanceRequestSubsector {
    private AssistanceRequestSubsectorId id;

    private AssistanceRequest request;

    private Subsector subsector;

    @EmbeddedId
    public AssistanceRequestSubsectorId getId() {
        return id;
    }

    @MapsId("requestId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "request_id", nullable = false)
    public AssistanceRequest getRequest() {
        return request;
    }

    @MapsId("subsectorId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "subsector_id", nullable = false)
    public Subsector getSubsector() {
        return subsector;
    }

}
