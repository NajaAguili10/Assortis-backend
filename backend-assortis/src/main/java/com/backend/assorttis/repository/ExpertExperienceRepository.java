package com.backend.assorttis.repository;

import com.backend.assorttis.entities.ExpertExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExpertExperienceRepository
        extends JpaRepository<ExpertExperience, Long>, JpaSpecificationExecutor<ExpertExperience> {
}
