package com.backend.assorttis.repository;

import com.backend.assorttis.entities.ProjectUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProjectUpdateRepository
        extends JpaRepository<ProjectUpdate, Long>, JpaSpecificationExecutor<ProjectUpdate> {
}
