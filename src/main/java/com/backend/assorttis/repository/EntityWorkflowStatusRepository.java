package com.backend.assorttis.repository;

import com.backend.assorttis.entities.EntityWorkflowStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EntityWorkflowStatusRepository
        extends JpaRepository<EntityWorkflowStatus, Long>, JpaSpecificationExecutor<EntityWorkflowStatus> {
}
