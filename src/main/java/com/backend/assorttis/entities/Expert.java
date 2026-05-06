package com.backend.assorttis.entities;

import com.backend.assorttis.entities.enums.VerificationStatus;
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

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "experts", schema = "public", indexes = {
        @Index(name = "idx_experts_primary_org", columnList = "primary_organization_id")
}, uniqueConstraints = {
        @UniqueConstraint(name = "experts_user_id_unique", columnNames = { "user_id" })
})
public class Expert {
    private Long id;

    private User user;

    private String fullName;
    private String bio;

    private String nationality;

    private Integer yearsExperience;

    private String currentPosition;

    private BigDecimal dailyRate;



    private String currency;

    private String Title;

    private Long completedMissions;

    private Long completedProjects;

    private Boolean remoteAvailable;

    private String availabilityStatus;

    private String profileSummary;

    private BigDecimal ratingAvg;

    private Boolean visibility;

    private Country country;

    private City city;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private VerificationStatus verificationStatus;

    private Integer ratingsCount;

    private Instant lastActiveAt;

    private Sector mainSector;

    private Integer studentsCount;

    private Boolean isBidWriter;

    private Instant createdAt;

    private String level;

    private Instant updatedAt;

    private BigDecimal hourlyRate;

    private BigDecimal availabilityPercentage;

    private Organization primaryOrganization;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    @Size(max = 255)
    @Column(name = "full_name")
    public String getFullName() {
        return fullName;
    }

    @Size(max = 100)
    @Column(name = "nationality", length = 100)
    public String getNationality() {
        return nationality;
    }

    @Column(name = "years_experience")
    public Integer getYearsExperience() {
        return yearsExperience;
    }

    @Size(max = 255)
    @Column(name = "current_position")
    public String getCurrentPosition() {
        return currentPosition;
    }

    @Column(name = "daily_rate")
    public BigDecimal getDailyRate() {
        return dailyRate;
    }

    @Size(max = 10)
    @Column(name = "currency", length = 10)
    public String getCurrency() {
        return currency;
    }

    @Column(name = "remote_available")
    public Boolean getRemoteAvailable() {
        return remoteAvailable;
    }

    @Size(max = 50)
    @Column(name = "availability_status", length = 50)
    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    @Column(name = "profile_summary", length = Integer.MAX_VALUE)
    public String getProfileSummary() {
        return profileSummary;
    }

    @Column(name = "rating_avg", precision = 3, scale = 2)
    public BigDecimal getRatingAvg() {
        return ratingAvg;
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

    @Column(name = "latitude", precision = 9, scale = 6)
    public BigDecimal getLatitude() {
        return latitude;
    }

    @Column(name = "longitude", precision = 9, scale = 6)
    public BigDecimal getLongitude() {
        return longitude;
    }


    @Enumerated(EnumType.STRING)
    @Column(name = "verification_status", length = 20)
    public VerificationStatus getVerificationStatus() {
        return verificationStatus;
    }

    @ColumnDefault("0")
    @Column(name = "ratings_count")
    public Integer getRatingsCount() {
        return ratingsCount;
    }

    @Column(name = "last_active_at")
    public Instant getLastActiveAt() {
        return lastActiveAt;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "main_sector_id")
    public Sector getMainSector() {
        return mainSector;
    }

    @ColumnDefault("0")
    @Column(name = "students_count")
    public Integer getStudentsCount() {
        return studentsCount;
    }

    @ColumnDefault("false")
    @Column(name = "is_bid_writer")
    public Boolean getIsBidWriter() {
        return isBidWriter;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

    @Size(max = 50)
    @Column(name = "level", length = 50)
    public String getLevel() {
        return level;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    public Instant getUpdatedAt() {
        return updatedAt;
    }

    @Column(name = "hourly_rate")
    public BigDecimal getHourlyRate() {
        return hourlyRate;
    }

    @Column(name = "availability_percentage", precision = 5, scale = 2)
    public BigDecimal getAvailabilityPercentage() {
        return availabilityPercentage;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "primary_organization_id")
    public Organization getPrimaryOrganization() {
        return primaryOrganization;
    }

}
