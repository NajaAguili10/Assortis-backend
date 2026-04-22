package com.backend.assorttis.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "expert_cv_downloads")
public class ExpertCvDownload {
    @Id
    @ColumnDefault("nextval('expert_cv_downloads_id_seq')")
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "expert_id", nullable = false)
    private Expert expert;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "downloaded_at")
    private Instant downloadedAt;

    @ColumnDefault("1")
    @Column(name = "credit_used")
    private Integer creditUsed;

}
