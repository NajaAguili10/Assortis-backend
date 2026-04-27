package com.backend.assorttis.config;

import com.backend.assorttis.entities.Expert;
import com.backend.assorttis.entities.ExpertSector;
import com.backend.assorttis.entities.ExpertSectorId;
import com.backend.assorttis.entities.Sector;
import com.backend.assorttis.repository.ExpertRepository;
import com.backend.assorttis.repository.ExpertSectorRepository;
import com.backend.assorttis.repository.SectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Component
@RequiredArgsConstructor
@Order(4)
public class ExpertSectorDataSeeder implements CommandLineRunner {

    private final ExpertRepository expertRepository;
    private final SectorRepository sectorRepository;
    private final ExpertSectorRepository expertSectorRepository;
    private final AtomicLong sectorIdCounter = new AtomicLong(500);

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Expert expert = expertRepository.findAll().stream().findFirst().orElse(null);
        if (expert == null) return;

        List<String> requiredSectors = Arrays.asList(
                "AGRICULTURE", "EDUCATION", "HEALTH", "INFRASTRUCTURE", 
                "GOVERNANCE", "ENVIRONMENT", "ENERGY", "WATER_SANITATION", 
                "FINANCE", "TECHNOLOGY", "HUMANITARIAN", "CLIMATE_CHANGE"
        );

        for (String code : requiredSectors) {
            Sector sector = sectorRepository.findByCode(code).orElseGet(() -> {
                Sector newSector = new Sector()
                        .setCode(code)
                        .setName(code.charAt(0) + code.substring(1).toLowerCase().replace("_", " "))
                        .setDescription(code + " sector");
                newSector.setId(sectorIdCounter.getAndIncrement());
                return sectorRepository.save(newSector);
            });

            ExpertSectorId id = new ExpertSectorId()
                    .setExpertId(expert.getId())
                    .setSectorId(sector.getId());
            
            if (!expertSectorRepository.existsById(id)) {
                ExpertSector expertSector = new ExpertSector()
                        .setId(id)
                        .setExpert(expert)
                        .setSector(sector);
                expertSectorRepository.save(expertSector);
            }
        }
    }
}
