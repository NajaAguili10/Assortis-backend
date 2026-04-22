package com.backend.assorttis.repository;

import com.backend.assorttis.entities.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {

    @Query("SELECT COUNT(p) FROM Project p WHERE p.status = 'ACTIVE'")
    Long countActiveProjects();

    @Query("SELECT COUNT(p) FROM Project p WHERE p.status = 'COMPLETED'")
    Long countCompletedProjects();

    @Query("SELECT COUNT(p) FROM Project p WHERE p.status = 'ON_HOLD'")
    Long countOnHoldProjects();

    @Query("SELECT COALESCE(SUM(p.budget), 0) FROM Project p")
    BigDecimal sumTotalBudget();

    // We need spent budget from expenses; that's in ProjectExpens table
    @Query("SELECT COALESCE(SUM(pe.amount), 0) FROM ProjectExpens pe WHERE pe.project.id = :projectId")
    BigDecimal sumSpentBudgetForProject(@Param("projectId") Long projectId);

    // For urgent projects
    @Query("SELECT COUNT(p) FROM Project p WHERE p.priority = 'URGENT'")
    Long countUrgentProjects();

    // For average completion, we need to compute from tasks or milestones? Could use timeline logic.
    // We'll compute in service.
}
