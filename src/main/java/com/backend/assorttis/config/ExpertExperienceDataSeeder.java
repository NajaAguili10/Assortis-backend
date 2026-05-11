package com.backend.assorttis.config;

import com.backend.assorttis.entities.*;
import com.backend.assorttis.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Component
@RequiredArgsConstructor
@Order(8)
public class ExpertExperienceDataSeeder implements CommandLineRunner {

    private final ExpertRepository expertRepository;
    private final ExpertExperienceRepository expertExperienceRepository;
    private final OrganizationRepository organizationRepository;
    private final SectorRepository sectorRepository;
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final ProjectRepository projectRepository;
    
    private final AtomicLong expIdCounter = new AtomicLong(1000);

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        List<Expert> experts = expertRepository.findAll();
        if (experts.isEmpty()) return;

        Organization org = organizationRepository.findAll().stream().findFirst().orElse(null);
        Sector sector = sectorRepository.findAll().stream().findFirst().orElse(null);
        Country country = countryRepository.findByCode("US").orElse(null);
        City city = cityRepository.findAll().stream().findFirst().orElse(null);

        for (int i = 0; i < experts.size(); i++) {
            Expert expert = experts.get(i);

            // Only assign if they don't have experiences already
            if (expertExperienceRepository.findByExpert(expert).isEmpty()) {
                ExpertExperience exp1 = new ExpertExperience()
                        .setId(expIdCounter.getAndIncrement())
                        .setExpert(expert)
                        .setTitle(i % 2 == 0 ? "Senior Project Manager" : "Lead Consultant")
                        .setOrganization(i % 2 == 0 ? "Global Development Corp" : "Strategic Partners Intl")
                        .setSector(sector)
                        .setDonorName(i % 2 == 0 ? "World Bank" : "EU Commission")
                        .setBudgetManaged(new BigDecimal("1500000.00"))
                        .setIsCurrent(true)
                        .setCountry(country)
                        .setCity(city)
                        .setStartDate(LocalDate.of(2021, 1, 1))
                        .setEndDate(null)
                        .setDescription("Responsible for overseeing large-scale international development projects and coordinating with stakeholders.")
                        .setOrganization1(org);

                expertExperienceRepository.save(exp1);

                // Add a past experience for all experts
                ExpertExperience exp2 = new ExpertExperience()
                        .setId(expIdCounter.getAndIncrement())
                        .setExpert(expert)
                        .setTitle("M&E Specialist")
                        .setOrganization("Development Solutions NGO")
                        .setSector(sector)
                        .setDonorName("USAID")
                        .setBudgetManaged(new BigDecimal("500000.00"))
                        .setIsCurrent(false)
                        .setCountry(country)
                        .setCity(city)
                        .setStartDate(LocalDate.of(2018, 3, 1))
                        .setEndDate(LocalDate.of(2020, 12, 31))
                        .setDescription("Conducted monitoring and evaluation of field programs and provided technical recommendations.");

                expertExperienceRepository.save(exp2);
            }
        }
    }
}
