package com.backend.assorttis.repository;

import com.backend.assorttis.entities.AssistanceRequestSubsector;
import com.backend.assorttis.entities.AssistanceRequestSubsectorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AssistanceRequestSubsectorRepository
        extends JpaRepository<AssistanceRequestSubsector, AssistanceRequestSubsectorId>,
        JpaSpecificationExecutor<AssistanceRequestSubsector> {
}
