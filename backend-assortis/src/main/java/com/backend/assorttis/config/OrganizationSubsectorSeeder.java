package com.backend.assorttis.config;

import com.backend.assorttis.entities.Organization;
import com.backend.assorttis.entities.OrganizationSubsector;
import com.backend.assorttis.entities.OrganizationSubsectorId;
import com.backend.assorttis.entities.Subsector;
import com.backend.assorttis.repository.OrganizationRepository;
import com.backend.assorttis.repository.OrganizationSubsectorRepository;
import com.backend.assorttis.repository.SubsectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Order(5)
public class OrganizationSubsectorSeeder implements CommandLineRunner {

    private final OrganizationRepository organizationRepository;
    private final SubsectorRepository subsectorRepository;
    private final OrganizationSubsectorRepository organizationSubsectorRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (organizationSubsectorRepository.count() > 0) {
            return;
        }

        List<Organization> organizations = organizationRepository.findAll();
        List<Subsector> subsectors = subsectorRepository.findAll();

        if (organizations.isEmpty() || subsectors.isEmpty()) {
            return;
        }

        // Give each organization the first available subsector as a relation
        for (Organization org : organizations) {
            Subsector subsector = subsectors.get((int) (org.getId() % subsectors.size()));
            
            OrganizationSubsector os = new OrganizationSubsector();
            OrganizationSubsectorId id = new OrganizationSubsectorId();
            id.setOrganizationId(org.getId());
            id.setSubsectorId(subsector.getId());
            os.setId(id);
            os.setOrganization(org);
            os.setSubsector(subsector);
            
            organizationSubsectorRepository.save(os);
        }
    }
}
