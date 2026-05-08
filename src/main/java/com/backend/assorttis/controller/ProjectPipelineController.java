package com.backend.assorttis.controller;

import com.backend.assorttis.dto.pipeline.ProjectPipelineEntryDTO;
import com.backend.assorttis.dto.project.PaginatedResponseDTO;
import com.backend.assorttis.service.ProjectPipelineService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/projects/pipeline")
@RequiredArgsConstructor
public class ProjectPipelineController {
    private final ProjectPipelineService projectPipelineService;

    @GetMapping
    public ResponseEntity<PaginatedResponseDTO<ProjectPipelineEntryDTO>> searchPipeline(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String stage,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "updatedAt,desc") String sort,
            Authentication authentication,
            @RequestHeader(value = "X-User-Email", required = false) String userEmail,
            @RequestHeader(value = "X-User-Role", required = false) String userRole
    ) {
        Pageable pageable = PageRequest.of(page, size, parseSort(sort));
        return ResponseEntity.ok(projectPipelineService.searchPipeline(search, stage, pageable, authentication, userEmail, userRole));
    }

    private Sort parseSort(String sort) {
        if (sort == null || sort.isBlank()) {
            return Sort.by(Sort.Direction.DESC, "updatedAt");
        }

        String[] parts = sort.split(",");
        String requestedField = parts[0];
        Sort.Direction direction = parts.length > 1 && "asc".equalsIgnoreCase(parts[1])
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        String field = switch (requestedField) {
            case "addedAt", "createdAt" -> "createdAt";
            case "probability" -> "probability";
            case "expectedValue", "value" -> "expectedValue";
            case "stage" -> "pipelineStage";
            default -> "updatedAt";
        };

        return Sort.by(direction, field);
    }
}
