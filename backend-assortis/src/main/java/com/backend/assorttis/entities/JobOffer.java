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
@Table(name = "job_offers", schema = "public")
public class JobOffer {
    private Long id;

    private Organization organization;

    private Project project;

    private String title;

    private String description;

    private Integer experienceRequired;

    private Boolean remote;

    private Boolean visibility;

    private Country country;

    private City city;

    private String contractType;

    private BigDecimal salaryMin;

    private BigDecimal salaryMax;

    private String currency;

    private LocalDate deadline;

    private String status;

    private Instant createdAt;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "organization_id")
    public Organization getOrganization() {
        return organization;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "project_id")
    public Project getProject() {
        return project;
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

    @Column(name = "experience_required")
    public Integer getExperienceRequired() {
        return experienceRequired;
    }

    @Column(name = "remote")
    public Boolean getRemote() {
        return remote;
    }

    @ColumnDefault("true")
    @Column(name = "visibility")
    public Boolean getVisibility() {
        return visibility;
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

    @Size(max = 50)
    @Column(name = "contract_type", length = 50)
    public String getContractType() {
        return contractType;
    }

    @Column(name = "salary_min")
    public BigDecimal getSalaryMin() {
        return salaryMin;
    }

    @Column(name = "salary_max")
    public BigDecimal getSalaryMax() {
        return salaryMax;
    }

    @Size(max = 10)
    @Column(name = "currency", length = 10)
    public String getCurrency() {
        return currency;
    }

    @Column(name = "deadline")
    public LocalDate getDeadline() {
        return deadline;
    }

    @Size(max = 50)
    @Column(name = "status", length = 50)
    public String getStatus() {
        return status;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

}
