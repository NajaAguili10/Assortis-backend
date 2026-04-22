package com.backend.assorttis.repository;

import com.backend.assorttis.entities.TenderSubsector;
import com.backend.assorttis.entities.TenderSubsectorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TenderSubsectorRepository
        extends JpaRepository<TenderSubsector, TenderSubsectorId>, JpaSpecificationExecutor<TenderSubsector> {
}
