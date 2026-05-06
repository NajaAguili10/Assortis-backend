package com.backend.assorttis.config;

import com.backend.assorttis.entities.Organization;
import com.backend.assorttis.entities.OrganizationCertification;
import com.backend.assorttis.repository.OrganizationCertificationRepository;
import com.backend.assorttis.repository.OrganizationRepository;
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
@Order(7)
public class OrganizationCertificationDataSeeder implements CommandLineRunner {

    private final OrganizationRepository organizationRepository;
    private final OrganizationCertificationRepository certificationRepository;

    private final AtomicLong certIdCounter = new AtomicLong(100);

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (certificationRepository.count() > 0) {
            return;
        }

        List<Organization> organizations = organizationRepository.findAll();

        if (organizations.isEmpty()) {
            return;
        }

        String[][] certPool = {
            {"ISO 9001:2015 Quality Management", "International Organization for Standardization", "QMS"},
            {"ISO 14001:2015 Environmental Management", "International Organization for Standardization", "ENV"},
            {"ISO/IEC 27001 Information Security", "International Organization for Standardization", "SEC"},
            {"ISO 45001 Occupational Health and Safety", "International Organization for Standardization", "OHS"},
            {"B Corp Certification", "B Lab", "BCORP"},
            {"LEED Certification", "U.S. Green Building Council", "LEED"},
            {"CMMI Level 3", "CMMI Institute", "CMMI"},
            {"Fair Trade Certified", "Fair Trade USA", "FTC"},
            {"SOC 2 Type II", "AICPA", "SOC2"}
        };

        for (Organization org : organizations) {
            int orgIndex = Math.abs(org.getId().hashCode());
            int numCerts = 1 + (orgIndex % 3); // Assign 1 to 3 certifications

            for (int i = 0; i < numCerts; i++) {
                int certIndex = (orgIndex + i) % certPool.length;
                String[] certData = certPool[certIndex];
                
                OrganizationCertification cert = new OrganizationCertification();
                cert.setId(certIdCounter.getAndIncrement());
                cert.setOrganization(org);
                cert.setCertificationName(certData[0]);
                cert.setIssuingOrganization(certData[1]);
                cert.setIssuedDate(LocalDate.now().minusYears(1 + (i % 3)).minusMonths(orgIndex % 12));
                cert.setExpiryDate(LocalDate.now().plusYears(1 + (i % 2)));
                cert.setCredentialId("CERT-" + certData[2] + "-" + org.getId() + "-" + i);
                cert.setCredentialUrl("https://verify.certification.org/CERT-" + certData[2] + "-" + org.getId() + "-" + i);
                cert.setCreatedAt(Instant.now());
                
                certificationRepository.save(cert);
            }
        }
    }
}
