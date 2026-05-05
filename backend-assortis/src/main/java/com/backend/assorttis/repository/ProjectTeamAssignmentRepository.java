package com.backend.assorttis.repository;


import com.backend.assorttis.entities.ProjectTeamAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectTeamAssignmentRepository extends JpaRepository<ProjectTeamAssignment, Long> {

    @Query("SELECT COUNT(pta) FROM ProjectTeamAssignment pta WHERE pta.project.id = :projectId")
    Integer countTeamMembersByProjectId(@Param("projectId") Long projectId);

    List<ProjectTeamAssignment> findByProjectId(Long projectId);
}
