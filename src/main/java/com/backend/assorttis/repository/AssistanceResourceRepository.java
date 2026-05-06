package com.backend.assorttis.repository;

import com.backend.assorttis.entities.AssistanceResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AssistanceResourceRepository
        extends JpaRepository<AssistanceResource, Long>, JpaSpecificationExecutor<AssistanceResource> {
}
