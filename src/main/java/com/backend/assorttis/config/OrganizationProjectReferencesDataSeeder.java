package com.backend.assorttis.config;

import com.backend.assorttis.entities.Country;
import com.backend.assorttis.entities.Donor;
import com.backend.assorttis.entities.Organization;
import com.backend.assorttis.entities.OrganizationProjectReference;
import com.backend.assorttis.entities.OrganizationProjectReferenceDocument;
import com.backend.assorttis.entities.Sector;
import com.backend.assorttis.entities.Subsector;
import com.backend.assorttis.repository.CountryRepository;
import com.backend.assorttis.repository.DonorRepository;
import com.backend.assorttis.repository.OrganizationProjectReferenceRepository;
import com.backend.assorttis.repository.OrganizationRepository;
import com.backend.assorttis.repository.SectorRepository;
import com.backend.assorttis.repository.SubsectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Component
@RequiredArgsConstructor
@Order(7)
public class OrganizationProjectReferencesDataSeeder implements CommandLineRunner {

    private final OrganizationProjectReferenceRepository referenceRepository;
    private final OrganizationRepository organizationRepository;
    private final CountryRepository countryRepository;
    private final SectorRepository sectorRepository;
    private final SubsectorRepository subsectorRepository;
    private final DonorRepository donorRepository;

    private final AtomicLong donorIdCounter = new AtomicLong(600L);

    @Override
    @Transactional
    public void run(String... args) {
        if (referenceRepository.count() > 0) {
            return;
        }

        List<Organization> organizations = organizationRepository.findAll().stream()
                .filter(organization -> Boolean.TRUE.equals(organization.getIsActive()) || organization.getIsActive() == null)
                .toList();

        if (organizations.isEmpty()) {
            return;
        }

        for (Organization organization : organizations) {
            seedOrganizationReferences(organization);
        }
    }

    private void seedOrganizationReferences(Organization organization) {
        Country country = ensureCountry(organization);
        Sector primarySector = resolvePrimarySector(organization);
        Subsector primarySubsector = resolvePrimarySubsector(primarySector);
        String regionWorld = inferRegionWorld(country);
        Country persistedCountry = ensureCountryRegionWorld(country, regionWorld);

        createReference(
                organization,
                persistedCountry,
                primarySector,
                primarySubsector,
                resolveDonor("World Bank", "WB", "MULTILATERAL"),
                "verified",
                "DOCUMENT",
                buildReferenceNumber(organization, 1),
                organization.getName() + " Service Delivery Strengthening",
                "Institutional support and operational delivery improvements for target communities.",
                "Comprehensive delivery package covering field implementation, coordination, monitoring, and reporting aligned with donor requirements.",
                resolveClientName(organization, "Ministry of Planning"),
                LocalDate.of(2023, 2, 1),
                LocalDate.of(2024, 12, 15),
                List.of(
                        buildDocument("Implementation report.pdf", "report", 1_480_000L),
                        buildDocument("Terms of reference.docx", "tor", 380_000L)
                )
        );

        createReference(
                organization,
                persistedCountry,
                primarySector,
                primarySubsector,
                resolveDonor("African Development Bank", "AFDB", "MULTILATERAL"),
                "notVerified",
                "LINK",
                buildReferenceNumber(organization, 2),
                organization.getName() + " Regional Capacity Building Programme",
                "Training, technical assistance, and operational backstopping across multi-stakeholder teams.",
                "Capacity-building assignment focused on governance, delivery quality, risk management, and beneficiary outreach across target territories.",
                resolveClientName(organization, "National Programme Coordination Unit"),
                LocalDate.of(2024, 1, 15),
                LocalDate.of(2025, 11, 30),
                List.of(
                        buildDocument("Inception note.pdf", "report", 620_000L)
                )
        );

        createReference(
                organization,
                persistedCountry,
                resolveSecondarySector(primarySector),
                resolvePrimarySubsector(resolveSecondarySector(primarySector)),
                resolveDonor("European Commission", "EC", "BILATERAL"),
                "verified",
                "NOTE",
                buildReferenceNumber(organization, 3),
                organization.getName() + " Targeted Technical Assistance Facility",
                "Advisory support for implementation planning, monitoring systems, and donor compliance.",
                "Technical assistance facility delivering short-term expertise, sector diagnostics, procurement support, and operational recommendations.",
                resolveClientName(organization, "Programme Management Office"),
                LocalDate.of(2022, 6, 1),
                LocalDate.of(2023, 10, 31),
                List.of(
                        buildDocument("Final completion report.pdf", "report", 2_220_000L)
                )
        );
    }

    private void createReference(
            Organization organization,
            Country country,
            Sector sector,
            Subsector subsector,
            Donor donor,
            String status,
            String referenceType,
            String referenceNumber,
            String title,
            String summary,
            String description,
            String client,
            LocalDate startDate,
            LocalDate endDate,
            List<OrganizationProjectReferenceDocument> documents
    ) {
        OrganizationProjectReference reference = new OrganizationProjectReference();
        reference.setOrganization(organization);
        reference.setReferenceNumber(referenceNumber);
        reference.setTitle(title);
        reference.setSummary(summary);
        reference.setDescription(description);
        reference.setCountry(country);
        reference.setRegion(null);
        reference.setSector(sector);
        reference.setSubsector(subsector);
        reference.setClient(client);
        reference.setDonor(donor);
        reference.setStartDate(startDate);
        reference.setEndDate(endDate);
        reference.setStatus(status);
        reference.setReferenceType(referenceType);
        reference.setUrl("LINK".equalsIgnoreCase(referenceType)
                ? "https://example.org/references/" + referenceNumber.toLowerCase(Locale.ROOT)
                : null);
        reference.setCreatedAt(Instant.now().minusSeconds(86_400L * 45));
        reference.setUpdatedAt(Instant.now().minusSeconds(86_400L * 3));

        documents.forEach(document -> document.setProjectReference(reference));
        reference.getDocuments().addAll(documents);

        referenceRepository.save(reference);
    }

    private OrganizationProjectReferenceDocument buildDocument(String name, String type, long size) {
        OrganizationProjectReferenceDocument document = new OrganizationProjectReferenceDocument();
        document.setName(name);
        document.setType(type);
        document.setUploadedAt(Instant.now().minusSeconds(86_400L * 5));
        document.setMimeType(name.endsWith(".docx")
                ? "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
                : "application/pdf");
        document.setSize(size);
        document.setContentDataUrl(null);
        return document;
    }

    private Country ensureCountry(Organization organization) {
        if (organization.getCountry() != null) {
            return organization.getCountry();
        }

        return countryRepository.findByCode("US")
                .or(() -> countryRepository.findByNameIgnoreCase("United States"))
                .orElseThrow();
    }

    private Country ensureCountryRegionWorld(Country country, String regionWorld) {
        if (country == null || regionWorld == null) {
            return country;
        }

        if (!regionWorld.equalsIgnoreCase(String.valueOf(country.getRegionWorld()))) {
            country.setRegionWorld(regionWorld);
            return countryRepository.save(country);
        }

        return country;
    }

    private Sector resolvePrimarySector(Organization organization) {
        if (organization.getMainSector() != null) {
            return organization.getMainSector();
        }

        return sectorRepository.findByCode("EDUCATION")
                .or(() -> sectorRepository.findByName("EDUCATION"))
                .or(() -> sectorRepository.findAll().stream().findFirst())
                .orElseThrow();
    }

    private Sector resolveSecondarySector(Sector primarySector) {
        return sectorRepository.findByCode("HEALTH")
                .or(() -> sectorRepository.findByName("HEALTH"))
                .or(() -> sectorRepository.findAll().stream()
                        .filter(sector -> primarySector == null || !sector.getId().equals(primarySector.getId()))
                        .findFirst())
                .orElse(primarySector);
    }

    private Subsector resolvePrimarySubsector(Sector sector) {
        if (sector == null || sector.getId() == null) {
            return null;
        }

        return subsectorRepository.findBySectorId(sector.getId()).stream().findFirst().orElse(null);
    }

    private Donor resolveDonor(String name, String shortName, String type) {
        Optional<Donor> existing = donorRepository.findByShortNameIgnoreCase(shortName)
                .or(() -> donorRepository.findByNameIgnoreCase(name));

        if (existing.isPresent()) {
            return existing.get();
        }

        Donor donor = new Donor();
        donor.setId(donorIdCounter.getAndIncrement());
        donor.setName(name);
        donor.setShortName(shortName);
        donor.setType(type);
        donor.setCreatedAt(Instant.now());
        return donorRepository.save(donor);
    }

    private String resolveClientName(Organization organization, String fallback) {
        if (organization.getCountry() != null && organization.getCountry().getName() != null) {
            return fallback + " - " + organization.getCountry().getName();
        }
        return fallback;
    }

    private String buildReferenceNumber(Organization organization, int sequence) {
        String orgToken = organization.getAcronym();
        if (orgToken == null || orgToken.isBlank()) {
            orgToken = organization.getName().replaceAll("[^A-Za-z0-9]", "").toUpperCase(Locale.ROOT);
        }
        if (orgToken.length() > 6) {
            orgToken = orgToken.substring(0, 6);
        }
        return orgToken + "-REF-" + LocalDate.now().getYear() + "-" + String.format("%03d", sequence);
    }

    private String inferRegionWorld(Country country) {
        if (country == null || country.getCode() == null) {
            return null;
        }

        return switch (country.getCode().toUpperCase(Locale.ROOT)) {
            case "US", "CA" -> "NORTH_AMERICA";
            case "FR", "DE", "GB", "ES", "IT", "BE", "NL", "CH", "AT", "PT" -> "WESTERN_EUROPE";
            case "ZM", "ZW", "BW", "NA", "ZA", "MZ" -> "SOUTHERN_AFRICA";
            case "KE", "TZ", "UG", "ET", "RW", "BI", "SO" -> "EAST_AFRICA";
            case "SN", "GH", "NG", "ML", "NE", "BF", "BJ", "TG", "CI" -> "WEST_AFRICA";
            case "MA", "TN", "DZ", "EG", "LY" -> "NORTH_AFRICA";
            default -> null;
        };
    }
}
