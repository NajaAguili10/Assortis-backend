package com.backend.assorttis.config;

import com.backend.assorttis.entities.*;
import com.backend.assorttis.entities.enums.VerificationStatus;
import com.backend.assorttis.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
                "UNICEF", "UNICEF", "INTERNATIONAL_ORG", 
                VerificationStatus.VERIFIED, "contact@unicef.org",
                "United States", "US", "New York", 
                "https://www.unicef.org", "https://logo.clearbit.com/unicef.org", 
                "United Nations Children's Fund",
                "123456789", 1946, 13000, new BigDecimal("5000000000.00"),
                true, new BigDecimal("4.8"), new BigDecimal("40.7128"), new BigDecimal("-74.0060"),
                "For every child", "123 UN Plaza", "USA", "10017", "John Doe", "Director"
        );

        // 2. World Bank
        createOrganization(
                "World Bank", "WB", "INTERNATIONAL_ORG", 
                VerificationStatus.VERIFIED, "info@worldbank.org",
                "United States", "US", "Washington D.C.", 
                "https://www.worldbank.org", "https://logo.clearbit.com/worldbank.org", 
                "Global financial institution providing loans and grants.",
                "987654321", 1944, 18900, new BigDecimal("30000000000.00"),
                true, new BigDecimal("4.5"), new BigDecimal("38.8951"), new BigDecimal("-77.0364"),
                "Working for a World Free of Poverty", "1818 H Street, NW", "USA", "20433", "Jane Smith", "President"
        );

        // 3. Red Cross
        createOrganization(
                "Red Cross", "ICRC", "NGO", 
                VerificationStatus.VERIFIED, "contact@redcross.org",
                "Switzerland", "CH", "Geneva", 
                "https://www.icrc.org", null, 
                "International humanitarian movement.",
                "CH12345", 1863, 20000, new BigDecimal("2000000000.00"),
                false, new BigDecimal("4.7"), new BigDecimal("46.2044"), new BigDecimal("6.1432"),
                "Humanity, Impartiality, Neutrality", "Avenue de la Paix 19", "Europe", "1202", "Alice Cross", "Coordinator"
        );

        // 4. Doctors Without Borders
        createOrganization(
                "Doctors Without Borders", "MSF", "NGO", 
                VerificationStatus.VERIFIED, "info@msf.org",
                "Switzerland", "CH", "Geneva", 
                "https://www.msf.org", null, 
                "International medical humanitarian organization.",
                "CH67890", 1971, 45000, new BigDecimal("1600000000.00"),
                false, new BigDecimal("4.9"), new BigDecimal("46.2044"), new BigDecimal("6.1432"),
                "Medical aid where it's needed most", "Rue de Lausanne 78", "Europe", "1211", "Bob Medic", "Field Director"
        );

        // 5. UNESCO
        createOrganization(
                "UNESCO", "UNESCO", "INTERNATIONAL_ORG", 
                VerificationStatus.VERIFIED, "contact@unesco.org",
                "France", "FR", "Paris", 
                "https://www.unesco.org", null, 
                "United Nations Educational, Scientific and Cultural Organization.",
                "FR112233", 1945, 2000, new BigDecimal("600000000.00"),
                true, new BigDecimal("4.6"), new BigDecimal("48.8566"), new BigDecimal("2.3522"),
                "Building peace in the minds of men and women", "7 Place de Fontenoy", "Europe", "75007", "Charlie Ed", "Secretary"
        );
    }

    private void createOrganization(String name, String acronym, String type, 
                                    VerificationStatus status, String email, 
                                    String countryName, String countryCode, String cityName, 
                                    String website, String logoUrl, String description,
                                    String regNumber, Integer yearFounded, Integer employees, BigDecimal turnover,
                                    Boolean isPartner, BigDecimal rating, BigDecimal lat, BigDecimal lng,
                                    String slogan, String address, String region, String postalCode,
                                    String contactName, String contactTitle) {
        
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
                .setCleanName(name.toLowerCase().replace(" ", "-"))
                .setLegalName(name + " Legal Entity")
                .setAcronym(acronym)
                .setType(type)
                .setRegistrationNumber(regNumber)
                .setYearFounded(yearFounded)
                .setEmployeesCount(employees)
                .setAnnualTurnover(turnover)
                .setVerificationStatus(status)
                .setContactEmail(email)
                .setContactPhone("+1234567890")
                .setCountry(country)
                .setCity(city)
                .setWebsite(website)
                .setLogoUrl(logoUrl)
                .setDescription(description)
                .setValidated(true)
                .setVerifiedAt(Instant.now())
                .setRatingAvg(rating)
                .setLatitude(lat)
                .setLongitude(lng)
                .setIsPartner(isPartner)
                .setSlogan(slogan)
                .setAddress(address)
                .setRegion(region)
                .setPostalCode(postalCode)
                .setEquipmentInfrastructure("Standard Office Equipment")
                .setContactName(contactName)
                .setContactTitle(contactTitle)
                .setProfileValidationStatus("APPROVED")
                .setIsActive(true)
                .setCreatedAt(Instant.now())
                .setUpdatedAt(Instant.now());

        organizationRepository.save(org);
    }
}
