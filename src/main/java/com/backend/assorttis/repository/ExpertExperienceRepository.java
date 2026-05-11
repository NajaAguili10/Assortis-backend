package com.backend.assorttis.repository;

import com.backend.assorttis.entities.Expert;
import com.backend.assorttis.entities.ExpertExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;

public interface ExpertExperienceRepository
        extends JpaRepository<ExpertExperience, Long>, JpaSpecificationExecutor<ExpertExperience> {
    List<ExpertExperience> findByExpert(Expert expert);
}
