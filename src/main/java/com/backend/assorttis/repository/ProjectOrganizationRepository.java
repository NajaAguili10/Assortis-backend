package com.backend.assorttis.repository;

import com.backend.assorttis.entities.ProjectOrganization;
import com.backend.assorttis.entities.ProjectOrganizationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ProjectOrganizationRepository extends JpaRepository<ProjectOrganization, ProjectOrganizationId> {

    @Query("SELECT po.organization.name FROM ProjectOrganization po WHERE po.project.id = :projectId AND po.isLead = true")
    String findLeadOrganizationNameByProjectId(@Param("projectId") Long projectId);

    @Query("SELECT po.organization.name FROM ProjectOrganization po WHERE po.project.id = :projectId AND po.isLead = false")
    List<String> findPartnerOrganizationNamesByProjectId(@Param("projectId") Long projectId);
}
