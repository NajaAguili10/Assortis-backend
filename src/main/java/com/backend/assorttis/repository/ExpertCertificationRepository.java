package com.backend.assorttis.repository;

import com.backend.assorttis.entities.ExpertCertification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExpertCertificationRepository
        extends JpaRepository<ExpertCertification, Long>, JpaSpecificationExecutor<ExpertCertification> {
}
