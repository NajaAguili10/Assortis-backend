package com.backend.assorttis.config;

import com.backend.assorttis.entities.Organization;
import com.backend.assorttis.entities.OrganizationSector;
import com.backend.assorttis.entities.OrganizationSectorId;
import com.backend.assorttis.entities.Sector;
import com.backend.assorttis.repository.OrganizationRepository;
import com.backend.assorttis.repository.OrganizationSectorRepository;
import com.backend.assorttis.repository.SectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Order(4)
public class OrganizationSectorSeeder implements CommandLineRunner {

    private final OrganizationRepository organizationRepository;
    private final SectorRepository sectorRepository;
    private final OrganizationSectorRepository organizationSectorRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (organizationSectorRepository.count() > 0) {
            return;
        }

        List<Organization> organizations = organizationRepository.findAll();
        List<Sector> sectors = sectorRepository.findAll();

        if (organizations.isEmpty() || sectors.isEmpty()) {
            return;
        }

        // Give each organization the first available sector as a relation
        for (Organization org : organizations) {
            Sector sector = sectors.get((int) (org.getId() % sectors.size()));
            
            OrganizationSector os = new OrganizationSector();
            OrganizationSectorId id = new OrganizationSectorId();
            id.setOrganizationId(org.getId());
            id.setSectorId(sector.getId());
            os.setId(id);
            os.setOrganization(org);
            os.setSector(sector);
            
            organizationSectorRepository.save(os);
        }
    }
}
