package com.backend.assorttis.repository;

import com.backend.assorttis.entities.TenderEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TenderEvaluationRepository
        extends JpaRepository<TenderEvaluation, Long>, JpaSpecificationExecutor<TenderEvaluation> {
}
