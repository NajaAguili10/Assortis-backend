package com.backend.assorttis.config;

import com.backend.assorttis.entities.City;
import com.backend.assorttis.entities.Region;
import com.backend.assorttis.repository.CityRepository;
import com.backend.assorttis.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

@Component
@RequiredArgsConstructor
@Order(9)
public class CityDataSeeder implements CommandLineRunner {

    private final CityRepository cityRepository;
    private final RegionRepository regionRepository;
    private final AtomicLong cityIdCounter = new AtomicLong(100);

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (cityRepository.count() == 0) {
            Region europe = getRegionByCode("EUROPE");
            Region africaEast = getRegionByCode("AFRICA_EAST");
            Region northAmerica = getRegionByCode("NORTH_AMERICA");

            createCity("Paris", europe, new BigDecimal("48.856614"), new BigDecimal("2.352222"), 2148000L, "Europe/Paris", true);
            createCity("Nairobi", africaEast, new BigDecimal("-1.292066"), new BigDecimal("36.821946"), 4397000L, "Africa/Nairobi", true);
            createCity("New York", northAmerica, new BigDecimal("40.712776"), new BigDecimal("-74.005974"), 8419000L, "America/New_York", false);
        }
    }

    private Region getRegionByCode(String code) {
        return regionRepository.findAll().stream()
                .filter(r -> code.equals(r.getCode()))
                .findFirst()
                .orElse(null);
    }

    private void createCity(String name, Region region, BigDecimal lat, BigDecimal lon, Long pop, String tz, Boolean isCapital) {
        City city = new City()
                .setId(cityIdCounter.getAndIncrement())
                .setName(name)
                .setRegion(region)
                .setLatitude(lat)
                .setLongitude(lon)
                .setPopulation(pop)
                .setTimezone(tz)
                .setIsCapital(isCapital);
        cityRepository.save(city);
    }
}
