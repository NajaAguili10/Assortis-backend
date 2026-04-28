package com.backend.assorttis.config;

import com.backend.assorttis.entities.Region;
import com.backend.assorttis.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Order(4)
public class RegionDataSeeder implements CommandLineRunner {

    private final RegionRepository regionRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (regionRepository.count() == 0) {
            List<Region> initialRegions = Arrays.asList(
                    new Region().setId(400L).setCode("AFRICA_WEST").setName("West Africa").setLatitude(new BigDecimal("12.000000")).setLongitude(new BigDecimal("-1.000000")),
                    new Region().setId(401L).setCode("AFRICA_EAST").setName("East Africa").setLatitude(new BigDecimal("-4.000000")).setLongitude(new BigDecimal("35.000000")),
                    new Region().setId(402L).setCode("AFRICA_CENTRAL").setName("Central Africa").setLatitude(new BigDecimal("1.000000")).setLongitude(new BigDecimal("21.000000")),
                    new Region().setId(403L).setCode("AFRICA_NORTH").setName("North Africa").setLatitude(new BigDecimal("28.000000")).setLongitude(new BigDecimal("14.000000")),
                    new Region().setId(404L).setCode("AFRICA_SOUTHERN").setName("Southern Africa").setLatitude(new BigDecimal("-26.000000")).setLongitude(new BigDecimal("24.000000")),
                    new Region().setId(405L).setCode("ASIA_PACIFIC").setName("Asia Pacific").setLatitude(new BigDecimal("15.000000")).setLongitude(new BigDecimal("110.000000")),
                    new Region().setId(406L).setCode("EUROPE").setName("Europe").setLatitude(new BigDecimal("48.000000")).setLongitude(new BigDecimal("15.000000")),
                    new Region().setId(407L).setCode("MIDDLE_EAST").setName("Middle East").setLatitude(new BigDecimal("26.000000")).setLongitude(new BigDecimal("45.000000")),
                    new Region().setId(408L).setCode("NORTH_AMERICA").setName("North America").setLatitude(new BigDecimal("40.000000")).setLongitude(new BigDecimal("-100.000000")),
                    new Region().setId(409L).setCode("SOUTH_AMERICA").setName("South America").setLatitude(new BigDecimal("-15.000000")).setLongitude(new BigDecimal("-60.000000")),
                    new Region().setId(410L).setCode("CARIBBEAN").setName("Caribbean").setLatitude(new BigDecimal("15.000000")).setLongitude(new BigDecimal("-75.000000")),
                    new Region().setId(411L).setCode("OCEANIA").setName("Oceania").setLatitude(new BigDecimal("-25.000000")).setLongitude(new BigDecimal("135.000000"))
            );

            for (Region region : initialRegions) {
                if (regionRepository.findAll().stream().noneMatch(r -> region.getCode().equals(r.getCode()))) {
                    regionRepository.save(region);
                }
            }
        }
    }
}
