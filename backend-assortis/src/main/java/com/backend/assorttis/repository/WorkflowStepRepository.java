package com.backend.assorttis.repository;

import com.backend.assorttis.entities.WorkflowStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WorkflowStepRepository
        extends JpaRepository<WorkflowStep, Long>, JpaSpecificationExecutor<WorkflowStep> {
}
