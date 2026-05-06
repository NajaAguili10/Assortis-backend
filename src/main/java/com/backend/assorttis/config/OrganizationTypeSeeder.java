package com.backend.assorttis.config;

import com.backend.assorttis.entities.OrganizationType;
import com.backend.assorttis.repository.OrganizationTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Order(3)
public class OrganizationTypeSeeder implements CommandLineRunner {

    private final OrganizationTypeRepository organizationTypeRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        String[][] types = {
                {"NGO", "Non-Governmental Organization", "Non-profit, citizen-based groups that function independently of government."},
                {"INTERNATIONAL_ORG", "International Organization", "Organizations with international membership, scope, or presence."},
                {"GOVERNMENT", "Government", "National or local government bodies and ministries."},
                {"GOVERNMENT_AGENCY", "Government Agency", "Specific agencies or departments under government control."},
                {"PRIVATE_SECTOR", "Private Sector", "For-profit businesses and companies not owned or operated by the government."},
                {"ACADEMIC", "Academic Institution", "Universities, colleges, and schools focused on education and research."},
                {"RESEARCH_INSTITUTION", "Research Institution", "Establishments dedicated to scientific or social research."},
                {"FOUNDATION", "Foundation", "Non-profit organizations that provide funding and support for other organizations."},
                {"CONSORTIUM", "Consortium", "Associations of two or more individuals, companies, or organizations."}
        };

        for (String[] t : types) {
            if (organizationTypeRepository.findById(t[0]).isEmpty()) {
                OrganizationType type = new OrganizationType()
                        .setCode(t[0])
                        .setLabel(t[1])
                        .setDescription(t[2]);
                organizationTypeRepository.save(type);
            }
        }
    }
}
