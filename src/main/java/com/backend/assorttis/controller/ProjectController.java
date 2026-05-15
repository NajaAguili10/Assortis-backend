package com.backend.assorttis.controller;


 import com.backend.assorttis.dto.project.*;
import com.backend.assorttis.entities.enums.project.ProjectPriorityEnum;
import com.backend.assorttis.entities.enums.project.ProjectStatus;
import com.backend.assorttis.entities.enums.project.ProjectTypeEnum;
import com.backend.assorttis.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

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
        filters.setStatus(parseEnumList(status, ProjectStatus.class, "status"));
        filters.setPriority(parseEnumList(priority, ProjectPriorityEnum.class, "priority"));
        filters.setType(parseEnumList(type, ProjectTypeEnum.class, "type"));
        filters.setSector(parseCsv(sector));
        filters.setSubsector(parseCsv(subsector));
        filters.setRegion(parseCsv(region));
        filters.setMinBudget(minBudget != null ? BigDecimal.valueOf(minBudget) : null);
        filters.setMaxBudget(maxBudget != null ? BigDecimal.valueOf(maxBudget) : null);
        filters.setStartDateFrom(parseDate(startDateFrom, "startDateFrom"));
        filters.setStartDateTo(parseDate(startDateTo, "startDateTo"));
        filters.setEndDateFrom(parseDate(endDateFrom, "endDateFrom"));
        filters.setEndDateTo(parseDate(endDateTo, "endDateTo"));
        filters.setLeadOrganization(parseCsv(leadOrganization));
        filters.setPartners(parseCsv(partners));

        Pageable pageable = PageRequest.of(page, size, parseSort(sortBy));
        PaginatedResponseDTO<ProjectListDTO> result = projectService.getProjects(filters, sortBy, pageable);
        return ResponseEntity.ok(result);
    }



    @GetMapping("/kpis")
    public ResponseEntity<ProjectKPIsDTO> getKPIs() {
        return ResponseEntity.ok(projectService.getKPIs());
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProjectListDTO>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDetailDTO> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
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

    private List<String> parseCsv(String raw) {
        if (raw == null || raw.isBlank()) {
            return null;
        }
        List<String> values = Arrays.stream(raw.split(","))
                .map(String::trim)
                .filter(value -> !value.isBlank())
                .toList();
        return values.isEmpty() ? null : values;
    }

    private <E extends Enum<E>> List<E> parseEnumList(String raw, Class<E> enumType, String parameterName) {
        List<String> values = parseCsv(raw);
        if (values == null) {
            return null;
        }

        return values.stream()
                .map(value -> parseEnum(value, enumType, parameterName))
                .filter(Objects::nonNull)
                .toList();
    }

    private <E extends Enum<E>> E parseEnum(String value, Class<E> enumType, String parameterName) {
        try {
            return Enum.valueOf(enumType, value.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid " + parameterName + " value: " + value
            );
        }
    }

    private LocalDate parseDate(String raw, String parameterName) {
        if (raw == null || raw.isBlank()) {
            return null;
        }
        try {
            return LocalDate.parse(raw.trim());
        } catch (DateTimeParseException exception) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid " + parameterName + " value: " + raw
            );
        }
    }
}
