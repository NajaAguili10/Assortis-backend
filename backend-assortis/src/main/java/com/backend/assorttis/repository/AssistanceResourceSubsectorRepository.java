package com.backend.assorttis.repository;

import com.backend.assorttis.entities.AssistanceResourceSubsector;
import com.backend.assorttis.entities.AssistanceResourceSubsectorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AssistanceResourceSubsectorRepository
        extends JpaRepository<AssistanceResourceSubsector, AssistanceResourceSubsectorId>,
        JpaSpecificationExecutor<AssistanceResourceSubsector> {
}
