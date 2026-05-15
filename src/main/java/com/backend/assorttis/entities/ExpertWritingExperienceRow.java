package com.backend.assorttis.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "expert_writing_experience_rows")
@Getter @Setter @NoArgsConstructor
public class ExpertWritingExperienceRow {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writing_experience_id", nullable = false)
    private ExpertWritingExperience writingExperience;

    @Column(name = "title_of_tender_project", length = 500)
    private String titleOfTenderProject;

    private String donor;

    private String country;

    private String year;

    @Column(name = "indicative_pages_written")
    private String indicativePagesWritten;

    private String result; // won, lost

    @Column(name = "reference_person_project_manager")
    private String referencePersonProjectManager;

    @Column(name = "additional_information", length = 1000)
    private String additionalInformation;
}
