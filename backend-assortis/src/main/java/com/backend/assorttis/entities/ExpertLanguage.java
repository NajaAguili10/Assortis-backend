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
@Table(name = "expert_languages", schema = "public")
public class ExpertLanguage {
    private ExpertLanguageId id;

    private Expert expert;

    private Language languageCode;

    private String proficiency;

    @EmbeddedId
    public ExpertLanguageId getId() {
        return id;
    }

    @MapsId("expertId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "expert_id", nullable = false)
    public Expert getExpert() {
        return expert;
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
