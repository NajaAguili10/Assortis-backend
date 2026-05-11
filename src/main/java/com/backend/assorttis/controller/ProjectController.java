package com.backend.assorttis.controller;


 import com.backend.assorttis.dto.project.*;
import com.backend.assorttis.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final com.backend.assorttis.service.ProjectSavedSearchService savedSearchService;

    @GetMapping
    public ResponseEntity<PaginatedResponseDTO<ProjectListDTO>> getProjects(
            @RequestParam(required = false) String searchQuery,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String sector,
            @RequestParam(required = false) String subsector,
            @RequestParam(required = false) String region,
            @RequestParam(required = false) Double minBudget,
            @RequestParam(required = false) Double maxBudget,
            @RequestParam(required = false) String startDateFrom,
            @RequestParam(required = false) String startDateTo,
            @RequestParam(required = false) String endDateFrom,
            @RequestParam(required = false) String endDateTo,
            @RequestParam(required = false) String leadOrganization,
            @RequestParam(required = false) String partners,
            @RequestParam(defaultValue = "newest") String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        // Build filters DTO from request parameters
        ProjectFiltersDTO filters = new ProjectFiltersDTO();
        filters.setSearchQuery(searchQuery);
        filters.setStatus(status != null ? List.of(status.split(",")) : null);
        filters.setPriority(priority != null ? List.of(priority.split(",")) : null);
        filters.setType(type != null ? List.of(type.split(",")) : null);
        filters.setSector(sector != null ? List.of(sector.split(",")) : null);
        filters.setSubsector(subsector != null ? List.of(subsector.split(",")) : null);
        filters.setRegion(region != null ? List.of(region.split(",")) : null);
        filters.setMinBudget(minBudget != null ? BigDecimal.valueOf(minBudget) : null);
        filters.setMaxBudget(maxBudget != null ? BigDecimal.valueOf(maxBudget) : null);
        // dates parsing omitted for brevity (use LocalDate.parse)
        filters.setLeadOrganization(leadOrganization != null ? List.of(leadOrganization.split(",")) : null);
        filters.setPartners(partners != null ? List.of(partners.split(",")) : null);

        Pageable pageable = PageRequest.of(page, size, parseSort(sortBy));
        PaginatedResponseDTO<ProjectListDTO> result = projectService.getProjects(filters, sortBy, pageable);
        return ResponseEntity.ok(result);
    }



    @GetMapping("/kpis")
    public ResponseEntity<ProjectKPIsDTO> getKPIs() {
        return ResponseEntity.ok(projectService.getKPIs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDetailDTO> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProjectListDTO>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    private Sort parseSort(String sortBy) {
        switch (sortBy) {
            case "newest":
                return Sort.by(Sort.Direction.DESC, "updatedAt");
            case "priority":
                // priority order is custom; we'll sort by priority enum order
                return Sort.by("priority").ascending();
            case "budget":
                return Sort.by(Sort.Direction.DESC, "budget");
            case "completion":
                // completion is computed, cannot sort directly in DB
                // we'll sort in memory in service or use a different approach
                return Sort.unsorted();
            case "name":
                return Sort.by(Sort.Direction.ASC, "title");
            default:
                return Sort.by(Sort.Direction.DESC, "updatedAt");
        }
    }

    @GetMapping("/saved-searches/{userId}")
    public ResponseEntity<List<ProjectSavedSearchDTO>> getSavedSearches(@PathVariable Long userId) {
        return ResponseEntity.ok(savedSearchService.getSavedSearches(userId));
    }

    @PostMapping("/saved-searches/{userId}")
    public ResponseEntity<ProjectSavedSearchDTO> saveSearch(
            @PathVariable Long userId,
            @RequestParam String name,
            @RequestBody java.util.Map<String, Object> payload
    ) {
        return ResponseEntity.ok(savedSearchService.saveSearch(userId, name, payload));
    }

    @DeleteMapping("/saved-searches/{id}")
    public ResponseEntity<Void> deleteSavedSearch(@PathVariable Long id) {
        savedSearchService.deleteSavedSearch(id);
        return ResponseEntity.noContent().build();
    }
}
