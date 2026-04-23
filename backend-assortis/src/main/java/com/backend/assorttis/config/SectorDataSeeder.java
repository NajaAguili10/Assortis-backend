package com.backend.assorttis.config;

import com.backend.assorttis.entities.Sector;
import com.backend.assorttis.repository.SectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicLong;

@Component
@RequiredArgsConstructor
@Order(2)
public class SectorDataSeeder implements CommandLineRunner {

    private final SectorRepository sectorRepository;
    private final AtomicLong sectorIdCounter = new AtomicLong(400);

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        String[][] sectors = {
                {"EDUCATION", "Education", "Projects related to primary, secondary, higher education and vocational training."},
                {"HEALTH", "Health", "Initiatives focusing on healthcare services, maternal health, and disease prevention."},
                {"AGRICULTURE", "Agriculture", "Development of sustainable farming, livestock management, and food security."},
                {"INFRASTRUCTURE", "Infrastructure", "Construction and rehabilitation of roads, buildings, and urban planning."},
                {"GOVERNANCE", "Governance", "Strengthening public administration, democratic processes, and justice reform."},
                {"ENVIRONMENT", "Environment", "Conservation of biodiversity, climate change adaptation, and sustainable development."},
                {"WATER_SANITATION", "Water & Sanitation", "Providing clean water supply, sanitation facilities, and hygiene education."},
                {"ENERGY", "Energy", "Promotion of renewable energy sources, solar power, and energy efficiency."},
                {"GENDER", "Gender", "Advancing gender equality and women's economic and political empowerment."},
                {"HUMAN_RIGHTS", "Human Rights", "Protection of civil, social, and individual rights for all citizens."},
                {"YOUTH", "Youth", "Programs targeting youth employment, skills development, and participation."},
                {"EMERGENCY_RESPONSE", "Emergency Response", "Immediate humanitarian aid and disaster relief in crisis situations."},
                {"OTHER", "Other", "Cross-cutting initiatives including research and technical assistance."}
        };

        for (String[] s : sectors) {
            if (sectorRepository.findByCode(s[0]).isEmpty()) {
                Sector sector = new Sector()
                        .setCode(s[0])
                        .setName(s[1])
                        .setDescription(s[2]);
                sector.setId(sectorIdCounter.getAndIncrement());
                sectorRepository.save(sector);
            }
        }
    }
}
