package com.backend.assorttis.config;

import com.backend.assorttis.entities.Country;
import com.backend.assorttis.repository.CountryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Component
@Order(2)
public class CountryDataSeeder implements CommandLineRunner {

    private final CountryRepository countryRepository;
    private final AtomicLong countryIdCounter;

    public CountryDataSeeder(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
        this.countryIdCounter = new AtomicLong(100);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        List<CountryInput> countries = List.of(
            new CountryInput("PL", "Poland", "CENTRAL_EASTERN_EUROPE"),
            new CountryInput("CZ", "Czech Republic", "CENTRAL_EASTERN_EUROPE"),
            new CountryInput("HU", "Hungary", "CENTRAL_EASTERN_EUROPE"),
            new CountryInput("SK", "Slovakia", "CENTRAL_EASTERN_EUROPE"),
            new CountryInput("SI", "Slovenia", "CENTRAL_EASTERN_EUROPE"),
            new CountryInput("AL", "Albania", "SOUTHEASTERN_EUROPE"),
            new CountryInput("BA", "Bosnia and Herzegovina", "SOUTHEASTERN_EUROPE"),
            new CountryInput("BG", "Bulgaria", "SOUTHEASTERN_EUROPE"),
            new CountryInput("HR", "Croatia", "SOUTHEASTERN_EUROPE"),
            new CountryInput("ME", "Montenegro", "SOUTHEASTERN_EUROPE"),
            new CountryInput("RS", "Serbia", "SOUTHEASTERN_EUROPE"),
            new CountryInput("FR", "France", "WESTERN_EUROPE"),
            new CountryInput("DE", "Germany", "WESTERN_EUROPE"),
            new CountryInput("GB", "United Kingdom", "WESTERN_EUROPE"),
            new CountryInput("ES", "Spain", "WESTERN_EUROPE"),
            new CountryInput("IT", "Italy", "WESTERN_EUROPE"),
            new CountryInput("BE", "Belgium", "WESTERN_EUROPE"),
            new CountryInput("NL", "Netherlands", "WESTERN_EUROPE"),
            new CountryInput("CH", "Switzerland", "WESTERN_EUROPE"),
            new CountryInput("AT", "Austria", "WESTERN_EUROPE"),
            new CountryInput("PT", "Portugal", "WESTERN_EUROPE"),
            new CountryInput("CM", "Cameroon", "CENTRAL_AFRICA"),
            new CountryInput("TD", "Chad", "CENTRAL_AFRICA"),
            new CountryInput("CF", "Central African Republic", "CENTRAL_AFRICA"),
            new CountryInput("CG", "Congo", "CENTRAL_AFRICA"),
            new CountryInput("CD", "Democratic Republic of Congo", "CENTRAL_AFRICA"),
            new CountryInput("GA", "Gabon", "CENTRAL_AFRICA"),
            new CountryInput("KE", "Kenya", "EAST_AFRICA"),
            new CountryInput("TZ", "Tanzania", "EAST_AFRICA"),
            new CountryInput("UG", "Uganda", "EAST_AFRICA"),
            new CountryInput("ET", "Ethiopia", "EAST_AFRICA"),
            new CountryInput("RW", "Rwanda", "EAST_AFRICA"),
            new CountryInput("BI", "Burundi", "EAST_AFRICA"),
            new CountryInput("SO", "Somalia", "EAST_AFRICA"),
            new CountryInput("EG", "Egypt", "NORTH_AFRICA"),
            new CountryInput("MA", "Morocco", "NORTH_AFRICA"),
            new CountryInput("TN", "Tunisia", "NORTH_AFRICA"),
            new CountryInput("DZ", "Algeria", "NORTH_AFRICA"),
            new CountryInput("LY", "Libya", "NORTH_AFRICA"),
            new CountryInput("ZA", "South Africa", "SOUTHERN_AFRICA"),
            new CountryInput("ZW", "Zimbabwe", "SOUTHERN_AFRICA"),
            new CountryInput("ZM", "Zambia", "SOUTHERN_AFRICA"),
            new CountryInput("MZ", "Mozambique", "SOUTHERN_AFRICA"),
            new CountryInput("BW", "Botswana", "SOUTHERN_AFRICA"),
            new CountryInput("NA", "Namibia", "SOUTHERN_AFRICA"),
            new CountryInput("NG", "Nigeria", "WEST_AFRICA"),
            new CountryInput("GH", "Ghana", "WEST_AFRICA"),
            new CountryInput("SN", "Senegal", "WEST_AFRICA"),
            new CountryInput("CI", "Cote d'Ivoire", "WEST_AFRICA"),
            new CountryInput("ML", "Mali", "WEST_AFRICA"),
            new CountryInput("NE", "Niger", "WEST_AFRICA"),
            new CountryInput("BF", "Burkina Faso", "WEST_AFRICA"),
            new CountryInput("BJ", "Benin", "WEST_AFRICA"),
            new CountryInput("TG", "Togo", "WEST_AFRICA"),
            new CountryInput("KZ", "Kazakhstan", "CENTRAL_ASIA"),
            new CountryInput("UZ", "Uzbekistan", "CENTRAL_ASIA"),
            new CountryInput("TM", "Turkmenistan", "CENTRAL_ASIA"),
            new CountryInput("KG", "Kyrgyzstan", "CENTRAL_ASIA"),
            new CountryInput("TJ", "Tajikistan", "CENTRAL_ASIA"),
            new CountryInput("SA", "Saudi Arabia", "MIDDLE_EAST"),
            new CountryInput("AE", "United Arab Emirates", "MIDDLE_EAST"),
            new CountryInput("QA", "Qatar", "MIDDLE_EAST"),
            new CountryInput("KW", "Kuwait", "MIDDLE_EAST"),
            new CountryInput("BH", "Bahrain", "MIDDLE_EAST"),
            new CountryInput("OM", "Oman", "MIDDLE_EAST"),
            new CountryInput("JO", "Jordan", "MIDDLE_EAST"),
            new CountryInput("LB", "Lebanon", "MIDDLE_EAST"),
            new CountryInput("IQ", "Iraq", "MIDDLE_EAST"),
            new CountryInput("YE", "Yemen", "MIDDLE_EAST"),
            new CountryInput("CN", "China", "NORTHEAST_ASIA"),
            new CountryInput("JP", "Japan", "NORTHEAST_ASIA"),
            new CountryInput("KR", "South Korea", "NORTHEAST_ASIA"),
            new CountryInput("KP", "North Korea", "NORTHEAST_ASIA"),
            new CountryInput("MN", "Mongolia", "NORTHEAST_ASIA"),
            new CountryInput("TH", "Thailand", "SOUTHEAST_ASIA"),
            new CountryInput("VN", "Vietnam", "SOUTHEAST_ASIA"),
            new CountryInput("PH", "Philippines", "SOUTHEAST_ASIA"),
            new CountryInput("ID", "Indonesia", "SOUTHEAST_ASIA"),
            new CountryInput("MY", "Malaysia", "SOUTHEAST_ASIA"),
            new CountryInput("SG", "Singapore", "SOUTHEAST_ASIA"),
            new CountryInput("MM", "Myanmar", "SOUTHEAST_ASIA"),
            new CountryInput("KH", "Cambodia", "SOUTHEAST_ASIA"),
            new CountryInput("IN", "India", "SOUTH_ASIA"),
            new CountryInput("PK", "Pakistan", "SOUTH_ASIA"),
            new CountryInput("BD", "Bangladesh", "SOUTH_ASIA"),
            new CountryInput("LK", "Sri Lanka", "SOUTH_ASIA"),
            new CountryInput("NP", "Nepal", "SOUTH_ASIA"),
            new CountryInput("AF", "Afghanistan", "SOUTH_ASIA"),
            new CountryInput("AU", "Australia", "OCEANIA"),
            new CountryInput("NZ", "New Zealand", "OCEANIA"),
            new CountryInput("FJ", "Fiji", "OCEANIA"),
            new CountryInput("PG", "Papua New Guinea", "OCEANIA"),
            new CountryInput("MX", "Mexico", "CENTRAL_AMERICA"),
            new CountryInput("GT", "Guatemala", "CENTRAL_AMERICA"),
            new CountryInput("HN", "Honduras", "CENTRAL_AMERICA"),
            new CountryInput("SV", "El Salvador", "CENTRAL_AMERICA"),
            new CountryInput("NI", "Nicaragua", "CENTRAL_AMERICA"),
            new CountryInput("CR", "Costa Rica", "CENTRAL_AMERICA"),
            new CountryInput("PA", "Panama", "CENTRAL_AMERICA"),
            new CountryInput("US", "United States", "NORTH_AMERICA"),
            new CountryInput("CA", "Canada", "NORTH_AMERICA")
        );

        for (CountryInput input : countries) {
            Country country = countryRepository.findByCode(input.code).orElseGet(() -> {
                Country nextCountry = new Country();
                nextCountry.setId(countryIdCounter.getAndIncrement());
                nextCountry.setCode(input.code);
                return nextCountry;
            });
            country.setName(input.name);
            country.setRegionWorld(input.regionWorld);
            country.setIsActive(true);
            countryRepository.save(country);
        }
    }

    private record CountryInput(String code, String name, String regionWorld) {}
}
