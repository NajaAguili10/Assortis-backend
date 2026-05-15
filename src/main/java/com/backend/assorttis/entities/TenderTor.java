package com.backend.assorttis.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "tender_tors", schema = "public")
public class TenderTor {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "tender_id", nullable = false)
    private Tender tender;

    @NotNull
    @ColumnDefault("1")
    @Column(name = "version", nullable = false)
    private Integer version;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "language")
    private Language language;

    @Size(max = 50)
    @ColumnDefault("'DRAFT'")
    @Column(name = "status", length = 50)
    private String status;

    @Size(max = 255)
    @Column(name = "title")
    private String title;

    @Size(max = 100)
    @Column(name = "reference_code", length = 100)
    private String referenceCode;

    @Column(name = "publication_date")
    private LocalDate publicationDate;

    @Column(name = "amendment_number")
    private Integer amendmentNumber;

    @Column(name = "context", length = Integer.MAX_VALUE)
    private String context;

    @Column(name = "objectives", length = Integer.MAX_VALUE)
    private String objectives;

    @Column(name = "scope_of_work", length = Integer.MAX_VALUE)
    private String scopeOfWork;

    @Column(name = "expected_results", length = Integer.MAX_VALUE)
    private String expectedResults;

    @Column(name = "expected_deliverables", length = Integer.MAX_VALUE)
    private String expectedDeliverables;

    @Column(name = "methodology", length = Integer.MAX_VALUE)
    private String methodology;

    @Column(name = "required_expertise", length = Integer.MAX_VALUE)
    private String requiredExpertise;

    @Column(name = "eligibility_criteria", length = Integer.MAX_VALUE)
    private String eligibilityCriteria;

    @Column(name = "experience_required")
    private Integer experienceRequired;   // À utiliser, ne pas ajouter experienceYears

    @Column(name = "team_composition", length = Integer.MAX_VALUE)
    private String teamComposition;

    @ColumnDefault("false")
    @Column(name = "key_experts_required")
    private Boolean keyExpertsRequired;

    @Column(name = "estimated_budget")
    private BigDecimal estimatedBudget;

    @Size(max = 10)
    @Column(name = "currency", length = 10)
    private String currency;

    @Column(name = "contract_duration")
    private Integer contractDuration;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Size(max = 100)
    @Column(name = "evaluation_method", length = 100)
    private String evaluationMethod;

    @Column(name = "technical_weight", precision = 5, scale = 2)
    private BigDecimal technicalWeight;

    @Column(name = "financial_weight", precision = 5, scale = 2)
    private BigDecimal financialWeight;

    @Column(name = "minimum_score_required", precision = 5, scale = 2)
    private BigDecimal minimumScoreRequired;

    @Column(name = "evaluation_criteria", length = Integer.MAX_VALUE)
    private String evaluationCriteria;

    @Column(name = "submission_guidelines", length = Integer.MAX_VALUE)
    private String submissionGuidelines;

    @Column(name = "submission_deadline")
    private Instant submissionDeadline;   // Utiliser celui-ci, pas de champ deadline supplémentaire

    @Column(name = "clarification_deadline")
    private Instant clarificationDeadline;

    @Column(name = "contact_information", length = Integer.MAX_VALUE)
    private String contactInformation;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "city_id")
    private City city;

    @ColumnDefault("false")
    @Column(name = "remote_allowed")
    private Boolean remoteAllowed;

    @Column(name = "keywords")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> keywords;

    @Column(name = "ai_summary", length = Integer.MAX_VALUE)
    private String aiSummary;

    @Size(max = 50)
    @Column(name = "complexity_level", length = 50)
    private String complexityLevel;

    @Column(name = "visibility_score", precision = 5, scale = 2)
    private BigDecimal visibilityScore;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    // ========== NOUVEAUX CHAMPS ==========
    @Size(max = 50)
    @Column(name = "type", length = 50)
    private String type; // CONSULTANT, TECHNICAL_EXPERT, PROJECT_MANAGER, TEAM_LEADER, SPECIALIST, ADVISOR, COORDINATOR, OTHER

    @Size(max = 100)
    @Column(name = "duration")
    private String duration; // ex: "6 months", "12 months"

    @Column(name = "in_pipeline")
    private Boolean inPipeline = false;
}
