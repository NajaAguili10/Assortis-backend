package com.backend.assorttis.config;

import com.backend.assorttis.entities.Organization;
import com.backend.assorttis.entities.Partnership;
import com.backend.assorttis.repository.OrganizationRepository;
import com.backend.assorttis.repository.PartnershipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Component
@RequiredArgsConstructor
@Order(3)
public class PartnershipDataSeeder implements CommandLineRunner {

    private final PartnershipRepository partnershipRepository;
    private final OrganizationRepository organizationRepository;

    private final AtomicLong partnershipIdCounter = new AtomicLong(100);

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        if (partnershipRepository.count() > 0) {
            return; // éviter duplication
        }

        List<Organization> organizations = organizationRepository.findAll();

        Organization unicef = findByName(organizations, "UNICEF");
        Organization worldBank = findByName(organizations, "World Bank");
        Organization redCross = findByName(organizations, "Red Cross");
        Organization msf = findByName(organizations, "Doctors Without Borders");
        Organization unesco = findByName(organizations, "UNESCO");

        // Création des partenariats
        createPartnership(unicef, worldBank, "Strategic partnership on education programs", "active", "strategic");
        createPartnership(redCross, msf, "Humanitarian medical collaboration", "active", "operational");
        createPartnership(unesco, unicef, "Joint cultural and education initiatives", "active", "programmatic");
    }

    private Organization findByName(List<Organization> list, String name) {
        return list.stream()
                .filter(o -> name.equalsIgnoreCase(o.getName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Organization not found: " + name));
    }

    private void createPartnership(Organization org1, Organization org2,
                                   String description, String status, String type) {

        Partnership partnership = new Partnership()
                .setId(partnershipIdCounter.getAndIncrement())
                .setOrganization(org1)
                .setPartnerOrganization(org2)
                .setDescription(description)
                .setStartDate(LocalDate.now().minusMonths(6))
                .setEndDate(null)
                .setStatus(status)
                .setType(type)
                .setCreatedAt(Instant.now());

        partnershipRepository.save(partnership);
    }
}