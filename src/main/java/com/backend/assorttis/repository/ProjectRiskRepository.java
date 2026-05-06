package com.backend.assorttis.repository;

import com.backend.assorttis.entities.ProjectRisk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProjectRiskRepository extends JpaRepository<ProjectRisk, Long>, JpaSpecificationExecutor<ProjectRisk> {
}
