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
        @UniqueConstraint(name = "experts_user_id_unique", columnNames = {"user_id"})
})
public class Expert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "user_id")
    private User user;

    @Size(max = 255)
    @Column(name = "full_name")
    private String fullName;

    // Nouveaux champs prénom / nom
    @Size(max = 100)
    @Column(name = "first_name", length = 100)
    private String firstName;

    @Size(max = 100)
    @Column(name = "last_name", length = 100)
    private String lastName;

    @Column(name = "bio", length = Integer.MAX_VALUE)
    private String bio;

    @Size(max = 100)
    @Column(name = "nationality", length = 100)
    private String nationality;

    @Column(name = "years_experience")
    private Integer yearsExperience;

    @Size(max = 255)
    @Column(name = "current_position")
    private String currentPosition;

    @Column(name = "daily_rate")
    private BigDecimal dailyRate;

    @Size(max = 10)
    @Column(name = "currency", length = 10)
    private String currency;

    @Size(max = 100)
    @Column(name = "title", length = 100)
    private String Title;

    @Column(name = "completed_missions")
    private Long completedMissions;

    @Column(name = "completed_projects")
    private Long completedProjects;

    @Column(name = "remote_available")
    private Boolean remoteAvailable;

    @Size(max = 50)
    @Column(name = "availability_status", length = 50)
    private String availabilityStatus;

    // Nouveau champ pour la disponibilité à court/moyen terme
    @Size(max = 30)
    @Column(name = "availability_timing", length = 30)
    private String availabilityTiming; // IMMEDIATE, WITHIN_1_MONTH, WITHIN_3_MONTHS, NOT_AVAILABLE

    @Column(name = "profile_summary", length = Integer.MAX_VALUE)
    private String profileSummary;

    @Column(name = "rating_avg", precision = 3, scale = 2)
    private BigDecimal ratingAvg;

    @ColumnDefault("true")
    @Column(name = "visibility")
    private Boolean visibility;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "city_id")
    private City city;

    @Column(name = "latitude", precision = 9, scale = 6)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 9, scale = 6)
    private BigDecimal longitude;

    @Enumerated(EnumType.STRING)
    @Column(name = "verification_status", length = 20)
    private VerificationStatus verificationStatus;

    // Nouveau champ : statut du compte (différent de verificationStatus)
    @Size(max = 20)
    @Column(name = "account_status", length = 20)
    private String accountStatus; // ACTIVE, INACTIVE, PENDING, VERIFIED, SUSPENDED

    @ColumnDefault("0")
    @Column(name = "ratings_count")
    private Integer ratingsCount;

    @Column(name = "last_active_at")
    private Instant lastActiveAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "main_sector_id")
    private Sector mainSector;

    @ColumnDefault("0")
    @Column(name = "students_count")
    private Integer studentsCount;

    @ColumnDefault("false")
    @Column(name = "is_bid_writer")
    private Boolean isBidWriter;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @Size(max = 50)
    @Column(name = "level", length = 50)
    private String level;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "hourly_rate")
    private BigDecimal hourlyRate;

    @Column(name = "availability_percentage", precision = 5, scale = 2)
    private BigDecimal availabilityPercentage;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "primary_organization_id")
    private Organization primaryOrganization;

    // Champs transients (calculés)
    @Transient
    private Integer activeProjects;

    @Transient
    private Double responseRate;

    @Transient
    private Integer profileCompleteness;

    // Getter pour verified dérivé de verificationStatus
    public Boolean getVerified() {
        return verificationStatus == VerificationStatus.VERIFIED;
    }
}
