// Updated service implementation
package com.backend.assorttis.service.impl;

import com.backend.assorttis.dto.project.*;
import com.backend.assorttis.entities.Project;
import com.backend.assorttis.entities.ProjectSubsector;
import com.backend.assorttis.entities.enums.project.ProjectStatus;
import com.backend.assorttis.mappers.ProjectMapper;
import com.backend.assorttis.repository.*;
import com.backend.assorttis.service.ProjectService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectExpensRepository expensRepository;
    private final ProjectTaskRepository taskRepository;
    private final ProjectMapper projectMapper;

    @Override
    @Transactional(readOnly = true)
    public PaginatedResponseDTO<ProjectListDTO> getProjects(ProjectFiltersDTO filters, String sortBy, Pageable pageable) {
        Specification<Project> spec = buildSpecification(filters);
        Page<Project> page = projectRepository.findAll(spec, pageable);
        List<ProjectListDTO> dtos = page.getContent().stream()
                .map(projectMapper::toProjectListDTO)
                .collect(Collectors.toList());

        PaginatedResponseDTO<ProjectListDTO> response = new PaginatedResponseDTO<>();
        response.setData(dtos);
        PaginatedResponseDTO.PaginationMetaDTO meta = new PaginatedResponseDTO.PaginationMetaDTO();
        meta.setPage(page.getNumber());
        meta.setPageSize(page.getSize());
        meta.setTotalItems(page.getTotalElements());
        meta.setTotalPages(page.getTotalPages());
        meta.setHasNextPage(page.hasNext());
        meta.setHasPreviousPage(page.hasPrevious());
        response.setMeta(meta);
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectKPIsDTO getKPIs() {
        ProjectKPIsDTO kpis = new ProjectKPIsDTO();
        kpis.setTotalProjects(projectRepository.count());
        kpis.setActiveProjects(projectRepository.countByStatus(ProjectStatus.ACTIVE));
        kpis.setCompletedProjects(projectRepository.countByStatus(ProjectStatus.COMPLETED));
        kpis.setOnHoldProjects(projectRepository.countByStatus(ProjectStatus.ON_HOLD));
        kpis.setTotalBudget(projectRepository.sumTotalBudget());
        kpis.setBudgetSpent(calculateTotalSpentBudget());
        kpis.setAverageCompletion(calculateAverageCompletion());
        kpis.setUrgentProjects(projectRepository.countByPriority("URGENT"));
        return kpis;
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectDetailDTO getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return projectMapper.toProjectDetailDTO(project);
    }

    private Specification<Project> buildSpecification(ProjectFiltersDTO filters) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filters.getSearchQuery() != null && !filters.getSearchQuery().isBlank()) {
                String like = "%" + filters.getSearchQuery().toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("title")), like),
                        cb.like(cb.lower(root.get("referenceCode")), like),
                        cb.like(cb.lower(root.get("description")), like)
                ));
            }

            if (filters.getStatus() != null && !filters.getStatus().isEmpty()) {
                predicates.add(root.get("status").in(filters.getStatus()));
            }
            if (filters.getPriority() != null && !filters.getPriority().isEmpty()) {
                predicates.add(root.get("priority").in(filters.getPriority()));
            }
            if (filters.getType() != null && !filters.getType().isEmpty()) {
                predicates.add(root.get("type").in(filters.getType()));
            }
            if (filters.getSector() != null && !filters.getSector().isEmpty()) {
                predicates.add(root.get("mainSector").get("name").in(filters.getSector()));
            }
            if (filters.getRegion() != null && !filters.getRegion().isEmpty()) {
                predicates.add(root.get("region").in(filters.getRegion()));
            }
            if (filters.getMinBudget() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("budget"), filters.getMinBudget()));
            }
            if (filters.getMaxBudget() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("budget"), filters.getMaxBudget()));
            }
            if (filters.getStartDateFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("startDate"), filters.getStartDateFrom()));
            }
            if (filters.getStartDateTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("startDate"), filters.getStartDateTo()));
            }
            if (filters.getEndDateFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("endDate"), filters.getEndDateFrom()));
            }
            if (filters.getEndDateTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("endDate"), filters.getEndDateTo()));
            }
            if (filters.getLeadOrganization() != null && !filters.getLeadOrganization().isEmpty()) {
                // Complex join logic can be implemented here or in a separate specification builder
            }
            if (filters.getPartners() != null && !filters.getPartners().isEmpty()) {
                // Similar
            }
            if (filters.getSubsector() != null && !filters.getSubsector().isEmpty()) {
                Join<Project, ProjectSubsector> subsectorJoin = root.join("projectSubsectors", JoinType.LEFT);
                predicates.add(subsectorJoin.get("subsector").get("name").in(filters.getSubsector()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private BigDecimal calculateTotalSpentBudget() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream()
                .map(p -> expensRepository.sumSpentBudgetByProjectId(p.getId()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Integer calculateAverageCompletion() {
        List<Project> projects = projectRepository.findAll();
        if (projects.isEmpty()) return 0;
        int total = 0;
        for (Project p : projects) {
            Integer totalTasks = taskRepository.countTotalTasksByProjectId(p.getId());
            Integer completedTasks = taskRepository.countCompletedTasksByProjectId(p.getId());
            if (totalTasks != null && totalTasks > 0) {
                total += (completedTasks * 100) / totalTasks;
            }
        }
        return total / projects.size();
    }
}
