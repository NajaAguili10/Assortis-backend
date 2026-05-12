package com.backend.assorttis.entities;

import com.backend.assorttis.entities.enums.project.ProjectPriorityEnum;
import com.backend.assorttis.entities.enums.project.ProjectStatus;
import com.backend.assorttis.entities.enums.project.ProjectTypeEnum;
import jakarta.persistence.*;
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
@Table(name = "projects", schema = "public", indexes = {
        @Index(name = "idx_projects_updated_at", columnList = "updated_at")
})
public class Project {
    private Long id;

    private String referenceCode;

    private String title;

    private String description;

    private Donor donor;

    private String fundingType;

    private BigDecimal budget;

    private String currency;

    private ProjectStatus status;


    private Map<String, Object> impactIndicators;

    private Country country;

    private City city;

    private LocalDate startDate;

    private LocalDate endDate;

    private Boolean isValidatedByOrg;

    private Instant validatedAt;

    private Organization validatedByOrg;

    private BigDecimal estimatedShare;

    private ProjectPriorityEnum  priority;

    private ProjectTypeEnum type;

    private Sector mainSector;

    private Map<String, Object> objectives;

    private Map<String, Object> deliverables;

    private Instant updatedAt;

    private String scope;

    private String source;

    private String region;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "description", length = Integer.MAX_VALUE)
    public String getDescription() {
        return description;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "donor_id")
    public Donor getDonor() {
        return donor;
    }

    @Size(max = 50)
    @Column(name = "funding_type", length = 50)
    public String getFundingType() {
        return fundingType;
    }

    @Column(name = "budget")
    public BigDecimal getBudget() {
        return budget;
    }

    @Size(max = 10)
    @Column(name = "currency", length = 10)
    public String getCurrency() {
        return currency;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50)
    public ProjectStatus getStatus() {
        return status;
    }

    @Column(name = "impact_indicators")
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getImpactIndicators() {
        return impactIndicators;
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

    @Column(name = "start_date")
    public LocalDate getStartDate() {
        return startDate;
    }

    @Column(name = "end_date")
    public LocalDate getEndDate() {
        return endDate;
    }

    @ColumnDefault("false")
    @Column(name = "is_validated_by_org")
    public Boolean getIsValidatedByOrg() {
        return isValidatedByOrg;
    }

    @Column(name = "validated_at")
    public Instant getValidatedAt() {
        return validatedAt;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "validated_by_org_id")
    public Organization getValidatedByOrg() {
        return validatedByOrg;
    }

    @Column(name = "estimated_share", precision = 5, scale = 2)
    public BigDecimal getEstimatedShare() {
        return estimatedShare;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", length = 20)
    public ProjectPriorityEnum getPriority() {
        return priority;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 50)
    public ProjectTypeEnum getType() {
        return type;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "main_sector_id")
    public Sector getMainSector() {
        return mainSector;
    }

    @Column(name = "objectives")
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getObjectives() {
        return objectives;
    }

    @Column(name = "deliverables")
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getDeliverables() {
        return deliverables;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    public Instant getUpdatedAt() {
        return updatedAt;
    }

    @Size(max = 100)
    @Column(name = "scope", length = 100)
    public String getScope() {
        return scope;
    }

    @Size(max = 50)
    @Column(name = "source", length = 50)
    public String getSource() {
        return source;
    }

    @Size(max = 255)
    @Column(name = "region")
    public String getRegion() {
        return region;
    }

}
