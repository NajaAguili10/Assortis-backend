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
@Table(name = "job_offer_languages", schema = "public")
public class JobOfferLanguage {
    private JobOfferLanguageId id;

    private JobOffer jobOffer;

    private Language languageCode;

    private String proficiency;

    @EmbeddedId
    public JobOfferLanguageId getId() {
        return id;
    }

    @MapsId("jobOfferId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "job_offer_id", nullable = false)
    public JobOffer getJobOffer() {
        return jobOffer;
    }

    @MapsId("languageCode")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "language_code", nullable = false)
    public Language getLanguageCode() {
        return languageCode;
    }

    @Size(max = 20)
    @Column(name = "proficiency", length = 20)
    public String getProficiency() {
        return proficiency;
    }

}
