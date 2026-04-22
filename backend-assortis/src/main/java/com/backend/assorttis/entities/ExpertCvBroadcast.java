package com.backend.assorttis.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "expert_cv_broadcasts")
public class ExpertCvBroadcast {
    @Id
    @ColumnDefault("nextval('expert_cv_broadcasts_id_seq')")
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "expert_id", nullable = false)
    private Expert expert;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "sent_at")
    private Instant sentAt;

    @Size(max = 20)
    @ColumnDefault("'pending'")
    @Column(name = "status", length = 20)
    private String status;

}
