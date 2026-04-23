package com.backend.assorttis.config;

import com.backend.assorttis.entities.Sector;
import com.backend.assorttis.entities.Subsector;
import com.backend.assorttis.repository.SectorRepository;
import com.backend.assorttis.repository.SubsectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.Map.entry;

@Component
@RequiredArgsConstructor
@Order(3)
public class SubsectorDataSeeder implements CommandLineRunner {

    private final SectorRepository sectorRepository;
    private final SubsectorRepository subsectorRepository;
    private final AtomicLong subsectorIdCounter = new AtomicLong(400);

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Map<String, String[]> subsectorsMap = Map.ofEntries(
            entry("EDUCATION", new String[]{"PRIMARY_EDUCATION", "SECONDARY_EDUCATION", "HIGHER_EDUCATION", "VOCATIONAL_TRAINING", "TEACHER_TRAINING", "CURRICULUM_DEVELOPMENT"}),
            entry("HEALTH", new String[]{"PRIMARY_HEALTHCARE", "MATERNAL_CHILD_HEALTH", "INFECTIOUS_DISEASES", "NUTRITION", "MENTAL_HEALTH", "HEALTH_SYSTEMS"}),
            entry("AGRICULTURE", new String[]{"CROP_PRODUCTION", "LIVESTOCK", "AGRIBUSINESS", "FOOD_SECURITY", "RURAL_DEVELOPMENT", "IRRIGATION"}),
            entry("INFRASTRUCTURE", new String[]{"ROADS_TRANSPORT", "BUILDINGS", "TELECOMMUNICATIONS", "URBAN_PLANNING", "PORTS_AIRPORTS", "RAILWAYS"}),
            entry("GOVERNANCE", new String[]{"PUBLIC_ADMINISTRATION", "DEMOCRATIC_GOVERNANCE", "JUSTICE_REFORM", "ANTI_CORRUPTION", "DECENTRALIZATION", "CIVIL_SOCIETY"}),
            entry("ENVIRONMENT", new String[]{"CLIMATE_CHANGE", "BIODIVERSITY", "WASTE_MANAGEMENT", "POLLUTION_CONTROL", "SUSTAINABLE_DEVELOPMENT", "FOREST_MANAGEMENT"}),
            entry("WATER_SANITATION", new String[]{"WATER_SUPPLY", "SANITATION", "HYGIENE", "WASTEWATER", "WATER_RESOURCES", "WASH"}),
            entry("ENERGY", new String[]{"RENEWABLE_ENERGY", "SOLAR", "WIND", "HYDROPOWER", "ENERGY_EFFICIENCY", "GRID_INFRASTRUCTURE"}),
            entry("GENDER", new String[]{"WOMENS_EMPOWERMENT", "GENDER_EQUALITY", "GBV_PREVENTION", "ECONOMIC_EMPOWERMENT", "POLITICAL_PARTICIPATION", "GIRLS_EDUCATION"}),
            entry("HUMAN_RIGHTS", new String[]{"CIVIL_RIGHTS", "SOCIAL_RIGHTS", "CHILD_RIGHTS", "INDIGENOUS_RIGHTS", "DISABILITY_RIGHTS", "REFUGEE_RIGHTS"}),
            entry("YOUTH", new String[]{"YOUTH_EMPLOYMENT", "YOUTH_PARTICIPATION", "YOUTH_SKILLS", "YOUTH_ENTREPRENEURSHIP", "YOUTH_HEALTH", "YOUTH_EDUCATION"}),
            entry("EMERGENCY_RESPONSE", new String[]{"DISASTER_RELIEF", "HUMANITARIAN_AID", "CONFLICT_RESPONSE", "REFUGEE_ASSISTANCE", "EARLY_RECOVERY", "PREPAREDNESS"}),
            entry("OTHER", new String[]{"RESEARCH", "CAPACITY_BUILDING", "TECHNICAL_ASSISTANCE", "MONITORING_EVALUATION", "CROSS_CUTTING", "MULTI_SECTORAL"})
        );

        for (Map.Entry<String, String[]> entry : subsectorsMap.entrySet()) {
            Sector sector = sectorRepository.findByCode(entry.getKey()).orElse(null);
            if (sector != null) {
                for (String subCode : entry.getValue()) {
                    if (subsectorRepository.findByCode(subCode).isEmpty()) {
                        Subsector sub = new Subsector()
                                .setSector(sector)
                                .setCode(subCode)
                                .setName(subCode.replace("_", " "));
                        sub.setId(subsectorIdCounter.getAndIncrement());
                        subsectorRepository.save(sub);
                    }
                }
            }
        }
    }
}
