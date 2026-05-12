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

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "tenders", schema = "public", indexes = {
        @Index(name = "idx_tenders_updated_at", columnList = "updated_at"),
        @Index(name = "idx_tenders_is_public", columnList = "is_public"),
        @Index(name = "idx_tenders_is_urgent", columnList = "is_urgent")
})
public class Tender {
    private Long id;

    private String referenceCode;

    private String title;

    private Donor donor;

    private Country country;

    private City city;

    private String description;

    private BigDecimal budgetMin;

    private BigDecimal budgetMax;

    private String currency;

    private Integer contractDuration;

    private Integer experienceRequired;

    private String procurementType;

    private Boolean isInternational;

    private LocalDate publicationDate;

    private LocalDate deadline;

    private TenderStatus status;

    private String sourceUrl;

    private BigDecimal visibilityScore;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private Instant cancelDate;

    private Boolean isCancelled;

    private Integer viewsCount;

    private String sourceName;

    private Sector mainSector;

    private Organization publishedByOrganization;

    private Instant createdAt;

    private BigDecimal estimatedBudget;

    private String contactEmail;

    private Instant updatedAt;

    private Boolean isPublic;

    private Boolean isUrgent;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Size(max = 100)
    @Column(name = "reference_code", length = 100)
    public String getReferenceCode() {
        return referenceCode;
    }

    @Size(max = 255)
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "donor_id")
    public Donor getDonor() {
        return donor;
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

    @Column(name = "description", length = Integer.MAX_VALUE)
    public String getDescription() {
        return description;
    }

    @Column(name = "budget_min")
    public BigDecimal getBudgetMin() {
        return budgetMin;
    }

    @Column(name = "budget_max")
    public BigDecimal getBudgetMax() {
        return budgetMax;
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

    @Column(name = "experience_required")
    public Integer getExperienceRequired() {
        return experienceRequired;
    }

    @Size(max = 50)
    @Column(name = "procurement_type", length = 50)
    public String getProcurementType() {
        return procurementType;
    }

    @Column(name = "is_international")
    public Boolean getIsInternational() {
        return isInternational;
    }

    @Column(name = "publication_date")
    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    @Column(name = "deadline")
    public LocalDate getDeadline() {
        return deadline;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "status")
    public TenderStatus getStatus() {
        return status;
    }

    @Column(name = "source_url", length = Integer.MAX_VALUE)
    public String getSourceUrl() {
        return sourceUrl;
    }

    @Column(name = "visibility_score", precision = 5, scale = 2)
    public BigDecimal getVisibilityScore() {
        return visibilityScore;
    }

    @Column(name = "latitude", precision = 9, scale = 6)
    public BigDecimal getLatitude() {
        return latitude;
    }

    @Column(name = "longitude", precision = 9, scale = 6)
    public BigDecimal getLongitude() {
        return longitude;
    }

    @Column(name = "cancel_date")
    public Instant getCancelDate() {
        return cancelDate;
    }

    @ColumnDefault("false")
    @Column(name = "is_cancelled")
    public Boolean getIsCancelled() {
        return isCancelled;
    }

    @ColumnDefault("0")
    @Column(name = "views_count")
    public Integer getViewsCount() {
        return viewsCount;
    }

    @Size(max = 100)
    @Column(name = "source_name", length = 100)
    public String getSourceName() {
        return sourceName;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "main_sector_id")
    public Sector getMainSector() {
        return mainSector;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "published_by_organization_id")
    public Organization getPublishedByOrganization() {
        return publishedByOrganization;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

    @Column(name = "estimated_budget")
    public BigDecimal getEstimatedBudget() {
        return estimatedBudget;
    }

    @Size(max = 255)
    @Column(name = "contact_email")
    public String getContactEmail() {
        return contactEmail;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    public Instant getUpdatedAt() {
        return updatedAt;
    }

    @ColumnDefault("true")
    @Column(name = "is_public")
    public Boolean getIsPublic() {
        return isPublic;
    }

    @ColumnDefault("false")
    @Column(name = "is_urgent")
    public Boolean getIsUrgent() {
        return isUrgent;
    }
    private String noticeType; // EARLY_INTELLIGENCE, FORECAST_PRIOR_NOTICE, PROJECT_NOTICE
    private String region; // peut être dérivé du pays
    private String alertCategory; // PROJECTS, AWARDS, SHORTLISTS, BIN
// Les champs suivants sont calculés (transients ou via @Formula) :
// proposalsCount, interestedOrgsCount, matchScore, mostRelevantPartnersCount, otherPossiblePartnersCount
}
