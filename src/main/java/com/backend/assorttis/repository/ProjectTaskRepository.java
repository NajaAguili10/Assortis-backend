package com.backend.assorttis.repository;

import com.backend.assorttis.entities.ProjectTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectTaskRepository extends JpaRepository<ProjectTask, Long> {

    @Query("SELECT COUNT(pt) FROM ProjectTask pt WHERE pt.project.id = :projectId")
    Integer countTotalTasksByProjectId(@Param("projectId") Long projectId);

    @Query("SELECT COUNT(pt) FROM ProjectTask pt WHERE pt.project.id = :projectId AND pt.status = 'COMPLETED'")
    Integer countCompletedTasksByProjectId(@Param("projectId") Long projectId);
}
