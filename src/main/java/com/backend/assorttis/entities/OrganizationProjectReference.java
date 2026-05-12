package com.backend.assorttis.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "organization_project_references")
@Getter @Setter @NoArgsConstructor
public class OrganizationProjectReference {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @Column(name = "reference_number", length = 100)
    private String referenceNumber;

    @Column(nullable = false)
    private String title;

    @Column(length = 500)
    private String summary;

    @Column(length = 4000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;

    @ManyToOne
    @JoinColumn(name = "sector_id")
    private Sector sector;

    @ManyToOne
    @JoinColumn(name = "subsector_id")
    private Subsector subsector;

    private String client;

    @ManyToOne
    @JoinColumn(name = "donor_id")
    private Donor donor;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(length = 20)
    private String status; // ongoing, completed

    @Column(name = "reference_type", length = 20)
    private String referenceType; // LINK, FILE, NOTE, DOCUMENT

    private String url;

    @OneToMany(mappedBy = "projectReference", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrganizationProjectReferenceDocument> documents = new ArrayList<>();

    @Column(name = "created_at")
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at")
    private Instant updatedAt = Instant.now();
}
