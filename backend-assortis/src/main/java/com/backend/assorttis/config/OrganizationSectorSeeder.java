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
@Order(4)
public class OrganizationSectorSeeder implements CommandLineRunner {

    private final SectorRepository sectorRepository;
    private final AtomicLong idCounter = new AtomicLong(500);

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        String[][] sectors = {
                {"EDUCATION", "Education", "Education and vocational training sectors."},
                {"HEALTH", "Health", "Healthcare, maternal health, and disease prevention."},
                {"AGRICULTURE", "Agriculture", "Sustainable farming and food security."},
                {"INFRASTRUCTURE", "Infrastructure", "Construction, roads, and urban planning."},
                {"GOVERNANCE", "Governance", "Public administration and justice reform."},
                {"ENVIRONMENT", "Environment", "Conservation and climate change initiatives."},
                {"WATER_SANITATION", "Water & Sanitation", "Clean water supply and hygiene education."},
                {"ENERGY", "Energy", "Renewable energy and energy efficiency."},
                {"GENDER", "Gender", "Gender equality and women's empowerment."},
                {"YOUTH", "Youth", "Youth employment and skills development."},
                {"HUMANITARIAN", "Humanitarian", "Humanitarian aid and emergency relief."},
                {"FINANCE", "Finance", "Financial services, banking, and microfinance."},
                {"TECHNOLOGY", "Technology", "Information technology and digital transformation."},
                {"CULTURE", "Culture", "Cultural heritage and creative industries."},
                {"TRADE", "Trade", "International trade and economic cooperation."}
        };

        for (String[] s : sectors) {
            if (sectorRepository.findByCode(s[0]).isEmpty()) {
                Sector sector = new Sector()
                        .setCode(s[0])
                        .setName(s[1])
                        .setDescription(s[2]);
                sector.setId(idCounter.getAndIncrement());
                sectorRepository.save(sector);
            }
        }
    }
}
