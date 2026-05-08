package com.backend.assorttis.config;

import com.backend.assorttis.entities.Country;
import com.backend.assorttis.repository.CountryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
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
        List<CountryInput> countries = Arrays.asList(


            new CountryInput("ZM", "Zambia"), new CountryInput("ZW", "Zimbabwe"),
            new CountryInput("US", "United States"), new CountryInput("FR", "France"),
            new CountryInput("GB", "United Kingdom"), new CountryInput("DE", "Germany"),
            new CountryInput("ES", "Spain"), new CountryInput("IT", "Italy"),
            new CountryInput("CA", "Canada"), new CountryInput("BE", "Belgium"),
            new CountryInput("CH", "Switzerland"), new CountryInput("NL", "Netherlands")
        );

        for (CountryInput input : countries) {
            if (countryRepository.findByCode(input.code).isEmpty()) {
                Country country = new Country();
                country.setId(countryIdCounter.getAndIncrement());
                country.setCode(input.code);
                country.setName(input.name);
                country.setIsActive(true);
                countryRepository.save(country);
            }
        }
    }

    private record CountryInput(String code, String name) {}
}
