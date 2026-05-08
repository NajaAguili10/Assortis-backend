package com.backend.assorttis.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "job_applications", schema = "public")
public class JobApplication {
    private Long id;

    private JobOffer jobOffer;

    private Expert expert;

    private Cv cv;

    private String status;

    private Instant appliedAt;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "job_offer_id")
    public JobOffer getJobOffer() {
        return jobOffer;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "expert_id")
    public Expert getExpert() {
        return expert;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "cv_id")
    public Cv getCv() {
        return cv;
    }

    @Size(max = 50)
    @Column(name = "status", length = 50)
    public String getStatus() {
        return status;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "applied_at")
    public Instant getAppliedAt() {
        return appliedAt;
    }

}
