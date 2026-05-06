package com.backend.assorttis.config;

import com.backend.assorttis.entities.Expert;
import com.backend.assorttis.entities.ExpertCertification;
import com.backend.assorttis.repository.ExpertRepository;
import com.backend.assorttis.repository.ExpertCertificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicLong;

@Component
@RequiredArgsConstructor
@Order(7)
public class ExpertCertificationDataSeeder implements CommandLineRunner {

    private final ExpertRepository expertRepository;
    private final ExpertCertificationRepository expertCertificationRepository;
    private final AtomicLong certIdCounter = new AtomicLong(1000);

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Expert expert = expertRepository.findAll().stream().findFirst().orElse(null);
        if (expert == null) return;

        if (expertCertificationRepository.count() == 0) {
            ExpertCertification cert1 = new ExpertCertification()
                    .setId(certIdCounter.getAndIncrement())
                    .setExpert(expert)
                    .setName("PMP - Project Management Professional")
                    .setIssuingOrganization("Project Management Institute (PMI)")
                    .setIssueDate(LocalDate.of(2020, 5, 15))
                    .setExpiryDate(LocalDate.of(2025, 5, 15))
                    .setCredentialId("PMI-1234567")
                    .setCredentialUrl("https://www.pmi.org/certifications/verify");
            
            ExpertCertification cert2 = new ExpertCertification()
                    .setId(certIdCounter.getAndIncrement())
                    .setExpert(expert)
                    .setName("Certified ScrumMaster (CSM)")
                    .setIssuingOrganization("Scrum Alliance")
                    .setIssueDate(LocalDate.of(2019, 8, 10))
                    .setExpiryDate(LocalDate.of(2024, 8, 10))
                    .setCredentialId("CSM-9876543")
                    .setCredentialUrl("https://www.scrumalliance.org/verify");

            expertCertificationRepository.save(cert1);
            expertCertificationRepository.save(cert2);
        }
    }
}
