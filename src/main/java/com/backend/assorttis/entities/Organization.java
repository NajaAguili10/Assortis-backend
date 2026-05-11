package com.backend.assorttis.entities;

import com.backend.assorttis.entities.enums.VerificationStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "organizations", schema = "public")
public class Organization {
    private Long id;
    private String name;
    private String cleanName;
    private String legalName;
    private String type;
    private String registrationNumber;
    private Integer yearFounded;
    private Integer employeesCount;
    private BigDecimal annualTurnover;
    private Country country;
    private City city;
    private String website;
    private String logoUrl;
    private String description;
    private Boolean validated;
    private Instant verifiedAt;
    private BigDecimal ratingAvg;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Boolean isPartner;
    private Organization parent;
    private OrganizationType typeCode;
    private String slogan;
    private String contactEmail;
    private String contactPhone;
    private String address;

    private VerificationStatus verificationStatus;
    private String profileValidationStatus;
    private User profileValidatedBy;
    private Instant profileValidatedAt;
    private Sector mainSector;
    private PaymentMethod defaultPaymentMethod;
    private Instant createdAt;
    private String acronym;
    private String region;
    private Boolean isActive;
    private Instant updatedAt;
    private String postalCode;
    private String equipmentInfrastructure;
    private String contactName;
    private String contactTitle;
    private String operatingRegionsRaw;
    private Long profileProjectsCompleted;
    private String cityNameOverride;
    private String countryNameOverride;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    @Size(max = 255)
    @Column(name = "clean_name")
    public String getCleanName() {
        return cleanName;
    }

    @Size(max = 255)
    @Column(name = "legal_name")
    public String getLegalName() {
        return legalName;
    }

    @Size(max = 50)
    @Column(name = "type", length = 50)
    public String getType() {
        return type;
    }

    @Size(max = 100)
    @Column(name = "registration_number", length = 100)
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    @Column(name = "year_founded")
    public Integer getYearFounded() {
        return yearFounded;
    }

    @Column(name = "employees_count")
    public Integer getEmployeesCount() {
        return employeesCount;
    }

    @Column(name = "annual_turnover")
    public BigDecimal getAnnualTurnover() {
        return annualTurnover;
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

    @Size(max = 255)
    @Column(name = "website")
    public String getWebsite() {
        return website;
    }

    @Column(name = "logo_url", length = Integer.MAX_VALUE)
    public String getLogoUrl() {
        return logoUrl;
    }

    @Column(name = "description", length = Integer.MAX_VALUE)
    public String getDescription() {
        return description;
    }

    @ColumnDefault("false")
    @Column(name = "validated")
    public Boolean getValidated() {
        return validated;
    }

    @Column(name = "verified_at")
    public Instant getVerifiedAt() {
        return verifiedAt;
    }

    @Column(name = "rating_avg", precision = 3, scale = 2)
    public BigDecimal getRatingAvg() {
        return ratingAvg;
    }

    @Column(name = "latitude", precision = 9, scale = 6)
    public BigDecimal getLatitude() {
        return latitude;
    }

    @Column(name = "longitude", precision = 9, scale = 6)
    public BigDecimal getLongitude() {
        return longitude;
    }

    @ColumnDefault("false")
    @Column(name = "is_partner")
    public Boolean getIsPartner() {
        return isPartner;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "parent_id")
    public Organization getParent() {
        return parent;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "type_code")
    public OrganizationType getTypeCode() {
        return typeCode;
    }

    @Column(name = "slogan", length = Integer.MAX_VALUE)
    public String getSlogan() {
        return slogan;
    }

    @Size(max = 255)
    @Column(name = "contact_email")
    public String getContactEmail() {
        return contactEmail;
    }

    @Size(max = 50)
    @Column(name = "contact_phone", length = 50)
    public String getContactPhone() {
        return contactPhone;
    }

    @Column(name = "address", length = Integer.MAX_VALUE)
    public String getAddress() {
        return address;
    }

    // 🔁 Changement : utilisation de l'enum avec @Enumerated(STRING)
    @ColumnDefault("'pending'")
    @Column(name = "verification_status", length = 20)
    @Enumerated(EnumType.STRING)
    public VerificationStatus getVerificationStatus() {
        return verificationStatus;
    }

    @Size(max = 20)
    @ColumnDefault("'pending'")
    @Column(name = "profile_validation_status", length = 20)
    public String getProfileValidationStatus() {
        return profileValidationStatus;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "profile_validated_by")
    public User getProfileValidatedBy() {
        return profileValidatedBy;
    }

    @Column(name = "profile_validated_at")
    public Instant getProfileValidatedAt() {
        return profileValidatedAt;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "main_sector_id")
    public Sector getMainSector() {
        return mainSector;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "default_payment_method_id")
    public PaymentMethod getDefaultPaymentMethod() {
        return defaultPaymentMethod;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

    @Size(max = 50)
    @Column(name = "acronym", length = 50)
    public String getAcronym() {
        return acronym;
    }

    @Size(max = 50)
    @Column(name = "region", length = 50)
    public String getRegion() {
        return region;
    }

    @ColumnDefault("true")
    @Column(name = "is_active")
    public Boolean getIsActive() {
        return isActive;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    public Instant getUpdatedAt() {
        return updatedAt;
    }

    @Size(max = 20)
    @Column(name = "postal_code", length = 20)
    public String getPostalCode() {
        return postalCode;
    }

    @Column(name = "equipment_infrastructure", length = Integer.MAX_VALUE)
    public String getEquipmentInfrastructure() {
        return equipmentInfrastructure;
    }

    @Size(max = 255)
    @Column(name = "contact_name")
    public String getContactName() {
        return contactName;
    }

    @Size(max = 255)
    @Column(name = "contact_title")
    public String getContactTitle() {
        return contactTitle;
    }

    @Column(name = "operating_regions", length = Integer.MAX_VALUE)
    public String getOperatingRegionsRaw() {
        return operatingRegionsRaw;
    }

    @Column(name = "profile_projects_completed")
    public Long getProfileProjectsCompleted() {
        return profileProjectsCompleted;
    }

    @Size(max = 255)
    @Column(name = "city_name_override")
    public String getCityNameOverride() {
        return cityNameOverride;
    }

    @Size(max = 255)
    @Column(name = "country_name_override")
    public String getCountryNameOverride() {
        return countryNameOverride;
    }
}
