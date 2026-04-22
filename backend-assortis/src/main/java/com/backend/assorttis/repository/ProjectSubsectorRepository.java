package com.backend.assorttis.repository;

import com.backend.assorttis.entities.ProjectSubsector;
import com.backend.assorttis.entities.ProjectSubsectorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ProjectSubsectorRepository extends JpaRepository<ProjectSubsector, ProjectSubsectorId> {

    @Query("SELECT ps.subsector.name FROM ProjectSubsector ps WHERE ps.project.id = :projectId")
    List<String> findSubsectorNamesByProjectId(@Param("projectId") Long projectId);
}
