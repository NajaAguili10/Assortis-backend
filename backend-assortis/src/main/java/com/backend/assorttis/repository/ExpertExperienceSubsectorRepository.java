package com.backend.assorttis.repository;

import com.backend.assorttis.entities.ExpertExperienceSubsector;
import com.backend.assorttis.entities.ExpertExperienceSubsectorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExpertExperienceSubsectorRepository
        extends JpaRepository<ExpertExperienceSubsector, ExpertExperienceSubsectorId>,
        JpaSpecificationExecutor<ExpertExperienceSubsector> {
}
