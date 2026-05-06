package com.backend.assorttis.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Embeddable
public class JobOfferLanguageId implements Serializable {
    private static final long serialVersionUID = -4315258152547586940L;
    private Long jobOfferId;

    private String languageCode;

    @NotNull
    @Column(name = "job_offer_id", nullable = false)
    public Long getJobOfferId() {
        return jobOfferId;
    }

    @Size(max = 10)
    @NotNull
    @Column(name = "language_code", nullable = false, length = 10)
    public String getLanguageCode() {
        return languageCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        JobOfferLanguageId entity = (JobOfferLanguageId) o;
        return Objects.equals(this.jobOfferId, entity.jobOfferId) &&
                Objects.equals(this.languageCode, entity.languageCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobOfferId, languageCode);
    }

}
