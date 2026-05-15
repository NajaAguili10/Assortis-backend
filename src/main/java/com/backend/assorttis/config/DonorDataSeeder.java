package com.backend.assorttis.config;

import com.backend.assorttis.entities.Donor;
import com.backend.assorttis.repository.DonorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Component
@RequiredArgsConstructor
@Order(6)
public class DonorDataSeeder implements CommandLineRunner {

    private final DonorRepository donorRepository;
    private final AtomicLong donorIdCounter = new AtomicLong(500L);

    @Override
    @Transactional
    public void run(String... args) {
        List<DonorInput> donors = List.of(
                new DonorInput("WB", "World Bank", "MULTILATERAL"),
                new DonorInput("AFDB", "African Development Bank", "MULTILATERAL"),
                new DonorInput("EC", "European Commission", "BILATERAL"),
                new DonorInput("EU_INSTITUTIONS", "European Union Institutions", "MULTILATERAL"),
                new DonorInput("UNDP", "United Nations Development Programme", "UN_AGENCY"),
                new DonorInput("UNICEF", "UNICEF", "UN_AGENCY"),
                new DonorInput("UNW", "UN Women", "UN_AGENCY"),
                new DonorInput("UNWFP", "World Food Programme", "UN_AGENCY"),
                new DonorInput("FAO", "Food and Agriculture Organization", "UN_AGENCY"),
                new DonorInput("USAID", "USAID", "BILATERAL"),
                new DonorInput("AFD", "Agence Francaise de Developpement", "BILATERAL"),
                new DonorInput("GIZ", "GIZ", "BILATERAL"),
                new DonorInput("JICA", "JICA", "BILATERAL"),
                new DonorInput("KFW", "KfW", "BILATERAL"),
                new DonorInput("IFAD", "IFAD", "MULTILATERAL"),
                new DonorInput("WHO", "World Health Organization", "UN_AGENCY"),
                new DonorInput("UNHCR", "UNHCR", "UN_AGENCY"),
                new DonorInput("EIB", "European Investment Bank", "MULTILATERAL")
        );

        for (DonorInput input : donors) {
            Donor donor = donorRepository.findByShortNameIgnoreCase(input.shortName)
                    .or(() -> donorRepository.findByNameIgnoreCase(input.name))
                    .orElseGet(() -> {
                        Donor nextDonor = new Donor();
                        nextDonor.setId(donorIdCounter.getAndIncrement());
                        return nextDonor;
                    });

            donor.setShortName(input.shortName);
            donor.setName(input.name);
            donor.setType(input.type);
            if (donor.getCreatedAt() == null) {
                donor.setCreatedAt(Instant.now());
            }
            donorRepository.save(donor);
        }
    }

    private record DonorInput(String shortName, String name, String type) {}
}
