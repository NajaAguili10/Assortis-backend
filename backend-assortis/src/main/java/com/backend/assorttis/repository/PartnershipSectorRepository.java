package com.backend.assorttis.repository;

import com.backend.assorttis.entities.PartnershipSector;
import com.backend.assorttis.entities.PartnershipSectorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PartnershipSectorRepository
        extends JpaRepository<PartnershipSector, PartnershipSectorId>, JpaSpecificationExecutor<PartnershipSector> {
}
