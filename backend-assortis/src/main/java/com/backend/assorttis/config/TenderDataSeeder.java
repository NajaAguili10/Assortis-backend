package com.backend.assorttis.config;

import com.backend.assorttis.entities.*;
import com.backend.assorttis.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

@Component
@RequiredArgsConstructor
@Order(2)
public class TenderDataSeeder implements CommandLineRunner {

    private final CountryRepository countryRepository;
    private final SectorRepository sectorRepository;
    private final SubsectorRepository subsectorRepository;
    private final TenderStatusRepository tenderStatusRepository;
    private final TenderRepository tenderRepository;
    private final TenderOrgInterestRepository tenderOrgInterestRepository;
    private final TenderSubsectorRepository tenderSubsectorRepository;
    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;

    private final Random random = new Random();

    // Compteurs pour les entités sans génération automatique d'ID
    private final AtomicLong countryIdCounter = new AtomicLong(1);
    private final AtomicLong sectorIdCounter = new AtomicLong(1);
    private final AtomicLong subsectorIdCounter = new AtomicLong(1);
    private final AtomicLong tenderIdCounter = new AtomicLong(1); // <-- AJOUTÉ

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (tenderRepository.count() > 0) {
            return;
        }

        Organization organization = organizationRepository.findAll().stream().findFirst()
                .orElseGet(() -> {
                    Organization org = new Organization()
                            .setName("Default Organization")
                            .setLegalName("Default Org Ltd.")
                            .setType("OTHER")
                            .setIsActive(true)
                            .setCreatedAt(Instant.now());
                    return organizationRepository.save(org);
                });

        User user = userRepository.findAll().stream().findFirst().orElse(null);

        seedReferenceData();
        seedTenders(organization, user);
    }

    private void seedReferenceData() {
        // Statuts (ID = code, pas de compteur)
        if (tenderStatusRepository.count() == 0) {
            createTenderStatus("DRAFT", "Brouillon");
            createTenderStatus("PUBLISHED", "Publié");
            createTenderStatus("CLOSED", "Clôturé");
            createTenderStatus("AWARDED", "Attribué");
            createTenderStatus("CANCELLED", "Annulé");
        }

        // Pays (ID numérique, assignation manuelle)
        if (countryRepository.count() == 0) {
            createCountry("FR", "France");
            createCountry("KE", "Kenya");
            createCountry("TZ", "Tanzania");
            createCountry("SN", "Senegal");
            createCountry("MA", "Morocco");
            createCountry("CI", "Côte d'Ivoire");
            createCountry("US", "United States");
        }

        // Secteurs (ID numérique, assignation manuelle)
        if (sectorRepository.count() == 0) {
            createSector("EDUCATION", "Education");
            createSector("HEALTH", "Health");
            createSector("AGRICULTURE", "Agriculture");
            createSector("INFRASTRUCTURE", "Infrastructure");
            createSector("GOVERNANCE", "Governance");
            createSector("ENVIRONMENT", "Environment");
            createSector("WATER_SANITATION", "Water & Sanitation");
            createSector("ENERGY", "Energy");
            createSector("GENDER", "Gender");
            createSector("HUMAN_RIGHTS", "Human Rights");
            createSector("YOUTH", "Youth");
            createSector("EMERGENCY_RESPONSE", "Emergency Response");
        }

        // Sous-secteurs (ID numérique, assignation manuelle)
        if (subsectorRepository.count() == 0) {
            Sector education = sectorRepository.findByCode("EDUCATION").orElseThrow();
            Sector health = sectorRepository.findByCode("HEALTH").orElseThrow();
            Sector agriculture = sectorRepository.findByCode("AGRICULTURE").orElseThrow();

            createSubsector(education, "PRIMARY_EDUCATION", "Primary Education");
            createSubsector(education, "SECONDARY_EDUCATION", "Secondary Education");
            createSubsector(education, "HIGHER_EDUCATION", "Higher Education");
            createSubsector(health, "PRIMARY_HEALTHCARE", "Primary Healthcare");
            createSubsector(health, "MATERNAL_HEALTH", "Maternal Health");
            createSubsector(agriculture, "CROP_PRODUCTION", "Crop Production");
            createSubsector(agriculture, "LIVESTOCK", "Livestock");
        }
    }

    private TenderStatus createTenderStatus(String code, String label) {
        TenderStatus status = new TenderStatus();
        status.setCode(code);
        status.setLabel(label);
        return tenderStatusRepository.save(status);
    }

    private Country createCountry(String code, String name) {
        Country country = new Country();
        country.setId(countryIdCounter.getAndIncrement());
        country.setCode(code);
        country.setName(name);
        return countryRepository.save(country);
    }

    private Sector createSector(String code, String name) {
        Sector sector = new Sector();
        sector.setId(sectorIdCounter.getAndIncrement());
        sector.setCode(code);
        sector.setName(name);
        return sectorRepository.save(sector);
    }

    private Subsector createSubsector(Sector sector, String code, String name) {
        Subsector subsector = new Subsector();
        subsector.setId(subsectorIdCounter.getAndIncrement());
        subsector.setSector(sector);
        subsector.setCode(code);
        subsector.setName(name);
        return subsectorRepository.save(subsector);
    }

    private void seedTenders(Organization organization, User user) {
        TenderStatus statusPublished = tenderStatusRepository.findByCode("PUBLISHED").orElseThrow();
        TenderStatus statusClosed = tenderStatusRepository.findByCode("CLOSED").orElseThrow();
        TenderStatus statusAwarded = tenderStatusRepository.findByCode("AWARDED").orElseThrow();
        TenderStatus statusDraft = tenderStatusRepository.findByCode("DRAFT").orElseThrow();

        List<Country> countries = countryRepository.findAll();
        List<Sector> sectors = sectorRepository.findAll();
        List<Subsector> subsectors = subsectorRepository.findAll();

        for (int i = 1; i <= 20; i++) {
            Tender tender = new Tender();

            // Assignation manuelle de l'ID
            tender.setId(tenderIdCounter.getAndIncrement());

            tender.setReferenceCode("TND-2026-" + String.format("%04d", i));

            String[] titles = {
                    "Digital Transformation in Education",
                    "Rural Health Infrastructure Project",
                    "Sustainable Agriculture Development",
                    "Water Supply and Sanitation Program",
                    "Renewable Energy Access Initiative",
                    "Governance and Anti-Corruption Support",
                    "Emergency Response to Flood Victims",
                    "Women Entrepreneurship Empowerment",
                    "Youth Employment and Skills Training",
                    "Climate Change Adaptation Fund",
                    "Infrastructure Rehabilitation in Post-Conflict Area",
                    "COVID-19 Response and Health System Strengthening",
                    "Forest Conservation and Biodiversity Protection",
                    "Election Observation Mission",
                    "Technical Assistance for Public Financial Management",
                    "Construction of Rural Roads",
                    "Support to Smallholder Farmers",
                    "Maternal and Child Health Program",
                    "Primary Education Quality Improvement",
                    "Waste Management and Circular Economy"
            };
            tender.setTitle(titles[i % titles.length]);
            tender.setDescription("Detailed description for " + tender.getTitle() + ". This is a comprehensive project aimed at delivering sustainable impact.");

            BigDecimal budget = BigDecimal.valueOf(100_000 + random.nextInt(4_900_000));
            tender.setEstimatedBudget(budget);
            tender.setCurrency(random.nextBoolean() ? "EUR" : "USD");

            LocalDate publication = LocalDate.now().minusDays(random.nextInt(30));
            tender.setPublicationDate(publication);
            LocalDate deadline = publication.plusDays(30 + random.nextInt(60));
            tender.setDeadline(deadline);
            tender.setCreatedAt(publication.atStartOfDay().toInstant(ZoneOffset.UTC));

            double r = random.nextDouble();
            TenderStatus status;
            if (r < 0.7) status = statusPublished;
            else if (r < 0.85) status = statusClosed;
            else if (r < 0.95) status = statusAwarded;
            else status = statusDraft;
            tender.setStatus(status);

            tender.setCountry(countries.get(random.nextInt(countries.size())));
            tender.setMainSector(sectors.get(random.nextInt(sectors.size())));
            tender.setPublishedByOrganization(organization);
            tender.setContactEmail("procurement@" + organization.getName().toLowerCase().replace(" ", "") + ".org");
            tender.setLatitude(BigDecimal.valueOf(-1.2921 + random.nextDouble() * 10));
            tender.setLongitude(BigDecimal.valueOf(36.8219 + random.nextDouble() * 10));
            tender.setIsPublic(true);
            tender.setIsUrgent(random.nextInt(10) > 7);
            tender.setViewsCount(random.nextInt(1000));
            tender.setIsInternational(random.nextBoolean());
            tender.setIsCancelled(false);
            tender.setProcurementType(random.nextBoolean() ? "OPEN" : "RESTRICTED");
            tender.setContractDuration(12 + random.nextInt(36));
            tender.setExperienceRequired(5 + random.nextInt(10));

            tender = tenderRepository.save(tender);

            // Ajout de sous-secteurs
            int subCount = 1 + random.nextInt(3);
            for (int j = 0; j < subCount; j++) {
                Subsector sub = subsectors.get(random.nextInt(subsectors.size()));
                if (sub.getSector().equals(tender.getMainSector())) {
                    TenderSubsector tenderSubsector = new TenderSubsector();
                    TenderSubsectorId id = new TenderSubsectorId()
                            .setTenderId(tender.getId())
                            .setSubsectorId(sub.getId());
                    tenderSubsector.setId(id);
                    tenderSubsector.setTender(tender);
                    tenderSubsector.setSubsector(sub);
                    tenderSubsectorRepository.save(tenderSubsector);
                }
            }

            // Intérêt d'organisation pour le pipeline
            if (random.nextBoolean()) {
                TenderOrgInterest interest = new TenderOrgInterest();
                TenderOrgInterestId interestId = new TenderOrgInterestId()
                        .setTenderId(tender.getId())
                        .setOrganizationId(organization.getId());
                interest.setId(interestId);
                interest.setTender(tender);
                interest.setOrganization(organization);
                interest.setRole(random.nextBoolean() ? "LEAD" : "PARTNER");
                interest.setMessage("We are interested in this tender");
                interest.setMatchScore(BigDecimal.valueOf(70 + random.nextInt(30)));
                interest.setAiRecommendation(random.nextBoolean());
                interest.setPipelineStage(random.nextBoolean() ? "QUALIFICATION" : "PROPOSAL");
                interest.setPriority(random.nextBoolean() ? "HIGH" : "MEDIUM");
                interest.setProgressPercent(random.nextInt(100));
                interest.setWantsContact(random.nextBoolean());
                interest.setRoleSought("CONSORTIUM");
                interest.setExpectedValue(budget.multiply(BigDecimal.valueOf(0.8 + random.nextDouble() * 0.4)));
                interest.setProbability(BigDecimal.valueOf(0.3 + random.nextDouble() * 0.6));
                interest.setCreatedAt(Instant.now());
                interest.setUpdatedAt(Instant.now());
                tenderOrgInterestRepository.save(interest);
            }
        }
    }
}
