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
    private Long id;

    private Tender tender;

    private Integer version;

    private Language language;

    private String status;

    private String title;

    private String referenceCode;

    private LocalDate publicationDate;

    private Integer amendmentNumber;

    private String context;

    private String objectives;

    private String scopeOfWork;

    private String expectedResults;

    private String expectedDeliverables;

    private String methodology;

    private String requiredExpertise;

    private String eligibilityCriteria;

    private Integer experienceRequired;

    private String teamComposition;

    private Boolean keyExpertsRequired;

    private BigDecimal estimatedBudget;

    private String currency;

    private Integer contractDuration;

    private LocalDate startDate;

    private LocalDate endDate;

    private String evaluationMethod;

    private BigDecimal technicalWeight;

    private BigDecimal financialWeight;

    private BigDecimal minimumScoreRequired;

    private String evaluationCriteria;

    private String submissionGuidelines;

    private Instant submissionDeadline;

    private Instant clarificationDeadline;

    private String contactInformation;

    private Country country;

    private City city;

    private Boolean remoteAllowed;

    private Map<String, Object> keywords;

    private String aiSummary;

    private String complexityLevel;

    private BigDecimal visibilityScore;

    private User createdBy;

    private Instant createdAt;

    private Instant updatedAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "tender_id", nullable = false)
    public Tender getTender() {
        return tender;
    }

    @NotNull
    @ColumnDefault("1")
    @Column(name = "version", nullable = false)
    public Integer getVersion() {
        return version;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "language")
    public Language getLanguage() {
        return language;
    }

    @Size(max = 50)
    @ColumnDefault("'DRAFT'")
    @Column(name = "status", length = 50)
    public String getStatus() {
        return status;
    }

    @Size(max = 255)
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    @Size(max = 100)
    @Column(name = "reference_code", length = 100)
    public String getReferenceCode() {
        return referenceCode;
    }

    @Column(name = "publication_date")
    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    @Column(name = "amendment_number")
    public Integer getAmendmentNumber() {
        return amendmentNumber;
    }

    @Column(name = "context", length = Integer.MAX_VALUE)
    public String getContext() {
        return context;
    }

    @Column(name = "objectives", length = Integer.MAX_VALUE)
    public String getObjectives() {
        return objectives;
    }

    @Column(name = "scope_of_work", length = Integer.MAX_VALUE)
    public String getScopeOfWork() {
        return scopeOfWork;
    }

    @Column(name = "expected_results", length = Integer.MAX_VALUE)
    public String getExpectedResults() {
        return expectedResults;
    }

    @Column(name = "expected_deliverables", length = Integer.MAX_VALUE)
    public String getExpectedDeliverables() {
        return expectedDeliverables;
    }

    @Column(name = "methodology", length = Integer.MAX_VALUE)
    public String getMethodology() {
        return methodology;
    }

    @Column(name = "required_expertise", length = Integer.MAX_VALUE)
    public String getRequiredExpertise() {
        return requiredExpertise;
    }

    @Column(name = "eligibility_criteria", length = Integer.MAX_VALUE)
    public String getEligibilityCriteria() {
        return eligibilityCriteria;
    }

    @Column(name = "experience_required")
    public Integer getExperienceRequired() {
        return experienceRequired;
    }

    @Column(name = "team_composition", length = Integer.MAX_VALUE)
    public String getTeamComposition() {
        return teamComposition;
    }

    @ColumnDefault("false")
    @Column(name = "key_experts_required")
    public Boolean getKeyExpertsRequired() {
        return keyExpertsRequired;
    }

    @Column(name = "estimated_budget")
    public BigDecimal getEstimatedBudget() {
        return estimatedBudget;
    }

    @Size(max = 10)
    @Column(name = "currency", length = 10)
    public String getCurrency() {
        return currency;
    }

    @Column(name = "contract_duration")
    public Integer getContractDuration() {
        return contractDuration;
    }

    @Column(name = "start_date")
    public LocalDate getStartDate() {
        return startDate;
    }

    @Column(name = "end_date")
    public LocalDate getEndDate() {
        return endDate;
    }

    @Size(max = 100)
    @Column(name = "evaluation_method", length = 100)
    public String getEvaluationMethod() {
        return evaluationMethod;
    }

    @Column(name = "technical_weight", precision = 5, scale = 2)
    public BigDecimal getTechnicalWeight() {
        return technicalWeight;
    }

    @Column(name = "financial_weight", precision = 5, scale = 2)
    public BigDecimal getFinancialWeight() {
        return financialWeight;
    }

    @Column(name = "minimum_score_required", precision = 5, scale = 2)
    public BigDecimal getMinimumScoreRequired() {
        return minimumScoreRequired;
    }

    @Column(name = "evaluation_criteria", length = Integer.MAX_VALUE)
    public String getEvaluationCriteria() {
        return evaluationCriteria;
    }

    @Column(name = "submission_guidelines", length = Integer.MAX_VALUE)
    public String getSubmissionGuidelines() {
        return submissionGuidelines;
    }

    @Column(name = "submission_deadline")
    public Instant getSubmissionDeadline() {
        return submissionDeadline;
    }

    @Column(name = "clarification_deadline")
    public Instant getClarificationDeadline() {
        return clarificationDeadline;
    }

    @Column(name = "contact_information", length = Integer.MAX_VALUE)
    public String getContactInformation() {
        return contactInformation;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "country_id")
    public Country getCountry() {
        return country;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "city_id")
    public City getCity() {
        return city;
    }

    @ColumnDefault("false")
    @Column(name = "remote_allowed")
    public Boolean getRemoteAllowed() {
        return remoteAllowed;
    }

    @Column(name = "keywords")
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getKeywords() {
        return keywords;
    }

    @Column(name = "ai_summary", length = Integer.MAX_VALUE)
    public String getAiSummary() {
        return aiSummary;
    }

    @Size(max = 50)
    @Column(name = "complexity_level", length = 50)
    public String getComplexityLevel() {
        return complexityLevel;
    }

    @Column(name = "visibility_score", precision = 5, scale = 2)
    public BigDecimal getVisibilityScore() {
        return visibilityScore;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "created_by")
    public User getCreatedBy() {
        return createdBy;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

    @Column(name = "updated_at")
    public Instant getUpdatedAt() {
        return updatedAt;
    }

}
