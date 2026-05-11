package com.backend.assorttis.repository;

import com.backend.assorttis.entities.ExpertCertification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import com.backend.assorttis.entities.Expert;

public interface ExpertCertificationRepository
        extends JpaRepository<ExpertCertification, Long>, JpaSpecificationExecutor<ExpertCertification> {
    List<ExpertCertification> findByExpert(Expert expert);
}
