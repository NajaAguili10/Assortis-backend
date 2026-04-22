package com.backend.assorttis.repository;

import com.backend.assorttis.entities.ExpertSector;
import com.backend.assorttis.entities.ExpertSectorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExpertSectorRepository
        extends JpaRepository<ExpertSector, ExpertSectorId>, JpaSpecificationExecutor<ExpertSector> {
}
