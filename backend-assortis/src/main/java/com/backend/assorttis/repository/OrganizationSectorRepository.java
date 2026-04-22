package com.backend.assorttis.repository;

import com.backend.assorttis.entities.OrganizationSector;
import com.backend.assorttis.entities.OrganizationSectorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrganizationSectorRepository
        extends JpaRepository<OrganizationSector, OrganizationSectorId>, JpaSpecificationExecutor<OrganizationSector> {
}
