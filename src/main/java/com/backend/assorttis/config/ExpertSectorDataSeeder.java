package com.backend.assorttis.config;

import com.backend.assorttis.entities.Expert;
import com.backend.assorttis.entities.ExpertSubscriptionSector;
import com.backend.assorttis.entities.ExpertSubscriptionSectorId;
import com.backend.assorttis.entities.Sector;
import com.backend.assorttis.repository.ExpertRepository;
import com.backend.assorttis.repository.ExpertSubscriptionSectorRepository;
import com.backend.assorttis.repository.SectorRepository;
import java.util.stream.Collectors;
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
    private final ExpertSubscriptionSectorRepository expertSubscriptionSectorRepository;
    private final AtomicLong sectorIdCounter = new AtomicLong(500);

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        List<Expert> experts = expertRepository.findAll();
        if (experts.isEmpty()) return;

        List<String> requiredSectors = Arrays.asList(
                "AGRICULTURE", "EDUCATION", "HEALTH", "INFRASTRUCTURE", 
                "GOVERNANCE", "ENVIRONMENT", "ENERGY", "WATER_SANITATION", 
                "FINANCE", "TECHNOLOGY", "HUMANITARIAN", "CLIMATE_CHANGE"
        );

        // Pre-fetch or create sectors
        List<Sector> sectors = requiredSectors.stream().map(code -> 
            sectorRepository.findByCode(code).orElseGet(() -> {
                Sector newSector = new Sector()
                        .setCode(code)
                        .setName(code.charAt(0) + code.substring(1).toLowerCase().replace("_", " "))
                        .setDescription(code + " sector");
                newSector.setId(sectorIdCounter.getAndIncrement());
                return sectorRepository.save(newSector);
            })
        ).collect(Collectors.toList());

        for (int i = 0; i < experts.size(); i++) {
            Expert expert = experts.get(i);

            // Create a highly mixed list based on index
            // Expert 0 gets (1 % 3) + 1 = 2 sectors starting at index 0
            // Expert 1 gets (2 % 3) + 1 = 3 sectors starting at index 1
            // Expert 2 gets (3 % 3) + 1 = 1 sector starting at index 2
            // etc.
            int count = (i % 3) + 1; 
            int startIndex = (i * 2) % sectors.size(); // Use multiplier to jump across the sector list
            
            List<Sector> sectorsToAssign = new java.util.ArrayList<>();
            for (int j = 0; j < count; j++) {
                sectorsToAssign.add(sectors.get((startIndex + j) % sectors.size()));
            }

            for (Sector sector : sectorsToAssign) {
                ExpertSubscriptionSectorId id = new ExpertSubscriptionSectorId();
                id.setExpertId(expert.getId());
                id.setSectorId(sector.getId());
                
                if (!expertSubscriptionSectorRepository.existsById(id)) {
                    ExpertSubscriptionSector expertSector = new ExpertSubscriptionSector();
                    expertSector.setId(id);
                    expertSector.setExpert(expert);
                    expertSector.setSector(sector);
                    expertSubscriptionSectorRepository.save(expertSector);
                }
            }
        }
    }
}
