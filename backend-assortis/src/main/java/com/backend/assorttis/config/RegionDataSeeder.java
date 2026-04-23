package com.backend.assorttis.config;

import com.backend.assorttis.entities.Region;
import com.backend.assorttis.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicLong;

@Component
@RequiredArgsConstructor
@Order(6)
public class RegionDataSeeder implements CommandLineRunner {

    private final RegionRepository regionRepository;
    private final AtomicLong regionIdCounter = new AtomicLong(400);

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        String[] regions = {
                "AFRICA_WEST", "AFRICA_EAST", "AFRICA_CENTRAL", "AFRICA_NORTH", "AFRICA_SOUTHERN",
                "ASIA_PACIFIC", "EUROPE", "MIDDLE_EAST", "NORTH_AMERICA", "SOUTH_AMERICA",
                "CARIBBEAN", "OCEANIA"
        };

        for (String rName : regions) {
            if (regionRepository.findAll().stream().noneMatch(r -> rName.equals(r.getCode()))) {
                Region region = new Region().setCode(rName).setName(rName.replace("_", " "));
                region.setId(regionIdCounter.getAndIncrement());
                regionRepository.save(region);
            }
        }
    }
}
