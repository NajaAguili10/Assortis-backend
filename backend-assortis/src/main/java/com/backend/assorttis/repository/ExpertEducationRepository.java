package com.backend.assorttis.repository;

import com.backend.assorttis.entities.ExpertEducation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExpertEducationRepository
        extends JpaRepository<ExpertEducation, Long>, JpaSpecificationExecutor<ExpertEducation> {
}
