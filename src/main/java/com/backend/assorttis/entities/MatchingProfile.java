package com.backend.assorttis.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "matching_profiles")
@Getter @Setter @NoArgsConstructor
public class MatchingProfile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String name;

    private boolean isActive = true;

    @Column(name = "alert_frequency", length = 20)
    private String alertFrequency = "daily"; // realtime, daily, weekly, none

    @Column(name = "created_at")
    private Instant createdAt = Instant.now();

    @Column(name = "match_count")
    private Integer matchCount = 0;

    // Relations many-to-many
    @ManyToMany
    @JoinTable(name = "matching_profile_sectors",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "sector_id"))
    private Set<Sector> sectors = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "matching_profile_countries",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id"))
    private Set<Country> countries = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "matching_profile_donors",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "donor_id"))
    private Set<Donor> donors = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "matching_profile_keywords",
            joinColumns = @JoinColumn(name = "profile_id"))
    @Column(name = "keyword", length = 100)
    private Set<String> keywords = new HashSet<>();
}
