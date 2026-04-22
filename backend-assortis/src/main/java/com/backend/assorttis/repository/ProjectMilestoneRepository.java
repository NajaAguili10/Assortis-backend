package com.backend.assorttis.repository;

import com.backend.assorttis.entities.ProjectMilestone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProjectMilestoneRepository
        extends JpaRepository<ProjectMilestone, Long>, JpaSpecificationExecutor<ProjectMilestone> {
}
