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
        Expert expert = expertRepository.findAll().stream().findFirst().orElse(null);
        if (expert == null) return;

        if (expertExperienceRepository.count() == 0) {
            Organization org = organizationRepository.findAll().stream().findFirst().orElse(null);
            Sector sector = sectorRepository.findAll().stream().findFirst().orElse(null);
            Country country = countryRepository.findAll().stream().findFirst().orElse(null);
            City city = cityRepository.findAll().stream().findFirst().orElse(null);
            Project project = projectRepository.findAll().stream().findFirst().orElse(null);

            ExpertExperience exp1 = new ExpertExperience()
                    .setId(expIdCounter.getAndIncrement())
                    .setExpert(expert)
                    .setTitle("Senior Project Manager")
                    .setOrganization("Global Development Corp")
                    .setOrganization1(org)
                    .setSector(sector)
                    .setDonorName("World Bank")
                    .setBudgetManaged(new BigDecimal("1500000.00"))
                    .setIsCurrent(true)
                    .setCountry(country)
                    .setCity(city)
                    .setStartDate(LocalDate.of(2021, 1, 1))
                    .setEndDate(null)
                    .setDescription("Managing large scale infrastructure projects across multiple regions.")
                    .setProject(project);

            ExpertExperience exp2 = new ExpertExperience()
                    .setId(expIdCounter.getAndIncrement())
                    .setExpert(expert)
                    .setTitle("M&E Specialist")
                    .setOrganization("Health Initiatives NGO")
                    .setOrganization1(null)
                    .setSector(sector)
                    .setDonorName("USAID")
                    .setBudgetManaged(new BigDecimal("500000.00"))
                    .setIsCurrent(false)
                    .setCountry(country)
                    .setCity(city)
                    .setStartDate(LocalDate.of(2018, 3, 1))
                    .setEndDate(LocalDate.of(2020, 12, 31))
                    .setDescription("Designed and implemented monitoring and evaluation frameworks for health programs.")
                    .setProject(null);

            expertExperienceRepository.save(exp1);
            expertExperienceRepository.save(exp2);
        }
    }
}
