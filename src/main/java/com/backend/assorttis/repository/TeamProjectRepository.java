package com.backend.assorttis.repository;

import com.backend.assorttis.entities.TeamProject;
import com.backend.assorttis.entities.TeamProjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TeamProjectRepository
        extends JpaRepository<TeamProject, TeamProjectId>, JpaSpecificationExecutor<TeamProject> {
}
