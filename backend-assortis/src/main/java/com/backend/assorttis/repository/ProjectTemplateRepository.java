package com.backend.assorttis.repository;

import com.backend.assorttis.entities.ProjectTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProjectTemplateRepository
        extends JpaRepository<ProjectTemplate, Long>, JpaSpecificationExecutor<ProjectTemplate> {
}
