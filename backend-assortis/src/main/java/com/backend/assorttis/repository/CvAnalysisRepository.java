package com.backend.assorttis.repository;

import com.backend.assorttis.entities.CvAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CvAnalysisRepository extends JpaRepository<CvAnalysis, Long>, JpaSpecificationExecutor<CvAnalysis> {
}
