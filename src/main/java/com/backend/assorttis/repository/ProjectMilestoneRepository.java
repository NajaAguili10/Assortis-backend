package com.backend.assorttis.repository;

import com.backend.assorttis.entities.ProjectMilestone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProjectMilestoneRepository
        extends JpaRepository<ProjectMilestone, Long>, JpaSpecificationExecutor<ProjectMilestone> {
    List<ProjectMilestone> findByProjectId(Long projectId);
}
