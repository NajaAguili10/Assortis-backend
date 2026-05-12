package com.backend.assorttis.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "expert_writing_experience")
@Getter @Setter @NoArgsConstructor
public class ExpertWritingExperience {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expert_id", nullable = false, unique = true)
    private Expert expert;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> writingMethodologies; // liste des méthodologies

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> writingContributions;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> writingLanguages;

    @Column(name = "comfortable_to_write_on", length = 2000)
    private String comfortableToWriteOn;

    @Column(name = "donor_procurement_experience", length = 2000)
    private String donorProcurementExperience;

    @Column(name = "writing_comments", length = 2000)
    private String writingComments;

    @OneToMany(mappedBy = "writingExperience", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExpertWritingExperienceRow> experienceRows = new ArrayList<>();
}
