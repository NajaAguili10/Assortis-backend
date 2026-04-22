package com.backend.assorttis.repository;

import com.backend.assorttis.entities.AssistanceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AssistanceRequestRepository
        extends JpaRepository<AssistanceRequest, Long>, JpaSpecificationExecutor<AssistanceRequest> {
}
