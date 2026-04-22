package com.backend.assorttis.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "bookmarked_experts")
public class  BookmarkedExpert {
    @EmbeddedId
    private BookmarkedExpertId id;

    @MapsId("organizationId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @MapsId("expertId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "expert_id", nullable = false)
    private Expert expert;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

}
