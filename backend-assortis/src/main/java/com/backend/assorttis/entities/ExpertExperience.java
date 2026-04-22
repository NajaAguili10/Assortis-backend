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
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "expert_experiences", schema = "public", indexes = {
        @Index(name = "idx_expert_experiences_project", columnList = "project_id")
})
public class ExpertExperience {
    private Long id;

    private Expert expert;

    private String title;

    private String organization;

    private Organization organization1;

    private Sector sector;

    private String donorName;

    private BigDecimal budgetManaged;

    private Boolean isCurrent;

    private Country country;

    private City city;

    private LocalDate startDate;

    private LocalDate endDate;

    private String description;

    private Project project;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "expert_id")
    public Expert getExpert() {
        return expert;
    }

    @Size(max = 255)
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    @Size(max = 255)
    @Column(name = "organization")
    public String getOrganization() {
        return organization;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "organization_id")
    public Organization getOrganization1() {
        return organization1;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "sector_id")
    public Sector getSector() {
        return sector;
    }

    @Size(max = 255)
    @Column(name = "donor_name")
    public String getDonorName() {
        return donorName;
    }

    @Column(name = "budget_managed")
    public BigDecimal getBudgetManaged() {
        return budgetManaged;
    }

    @Column(name = "is_current")
    public Boolean getIsCurrent() {
        return isCurrent;
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

    @Column(name = "description", length = Integer.MAX_VALUE)
    public String getDescription() {
        return description;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "project_id")
    public Project getProject() {
        return project;
    }

}
