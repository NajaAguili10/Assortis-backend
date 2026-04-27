package com.backend.assorttis.config;

import com.backend.assorttis.entities.*;
import com.backend.assorttis.entities.enums.VerificationStatus;
import com.backend.assorttis.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

@Component
@RequiredArgsConstructor
@Order(2)
public class OrganizationDataSeeder implements CommandLineRunner {

    private final OrganizationRepository organizationRepository;
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;

    private final AtomicLong countryIdCounter = new AtomicLong(300);
    private final AtomicLong cityIdCounter = new AtomicLong(300);

    @Override

    @Transactional
    public void run(String... args) throws Exception {
        if (organizationRepository.count() > 1) {
            return; // Skip if already seeded (Assortis Tech is usually order 1)
        }

        // 1. UNICEF
        createOrganization(
                "UNICEF", "UNICEF", "International Organization", 
                VerificationStatus.VERIFIED, "contact@unicef.org",
                "United States", "US", "New York", 
                "https://www.unicef.org", "https://logo.clearbit.com/unicef.org", 
                "United Nations Children's Fund"
        );

        // 2. World Bank
        createOrganization(
                "World Bank", "WB", "International Organization", 
                VerificationStatus.VERIFIED, "info@worldbank.org",
                "United States", "US", "Washington D.C.", 
                "https://www.worldbank.org", "https://logo.clearbit.com/worldbank.org", 
                "Global financial institution providing loans and grants."
        );

        // 3. Red Cross
        createOrganization(
                "Red Cross", "ICRC", "NGO", 
                VerificationStatus.VERIFIED, "contact@redcross.org",
                "Switzerland", "CH", "Geneva", 
                "https://www.icrc.org", null, 
                "International humanitarian movement."
        );

        // 4. Doctors Without Borders
        createOrganization(
                "Doctors Without Borders", "MSF", "NGO", 
                VerificationStatus.VERIFIED, "info@msf.org",
                "Switzerland", "CH", "Geneva", 
                "https://www.msf.org", null, 
                "International medical humanitarian organization."
        );

        // 5. UNESCO
        createOrganization(
                "UNESCO", "UNESCO", "International Organization", 
                VerificationStatus.VERIFIED, "contact@unesco.org",
                "France", "FR", "Paris", 
                "https://www.unesco.org", null, 
                "United Nations Educational, Scientific and Cultural Organization."
        );
    }

    private void createOrganization(String name, String acronym, String type, 
                                    VerificationStatus status, String email, 
                                    String countryName, String countryCode, String cityName, 
                                    String website, String logoUrl, String description) {
        
        Country country = countryRepository.findAll().stream()
                .filter(c -> countryCode.equals(c.getCode()))
                .findFirst()
                .orElseGet(() -> {
                    Country c = new Country().setName(countryName).setCode(countryCode);
                    c.setId(countryIdCounter.getAndIncrement());
                    return countryRepository.save(c);
                });

        City city = cityRepository.findAll().stream()
                .filter(c -> cityName.equals(c.getName()))
                .findFirst()
                .orElseGet(() -> {
                    City ct = new City().setName(cityName);
                    ct.setId(cityIdCounter.getAndIncrement());
                    return cityRepository.save(ct);
                });

        Organization org = new Organization()
                .setName(name)
                .setAcronym(acronym)
                .setType(type)
                .setVerificationStatus(status)
                .setContactEmail(email)
                .setCountry(country)
                .setCity(city)
                .setWebsite(website)
                .setLogoUrl(logoUrl)
                .setDescription(description)
                .setIsActive(true)
                .setCreatedAt(Instant.now());

        organizationRepository.save(org);
    }
}
