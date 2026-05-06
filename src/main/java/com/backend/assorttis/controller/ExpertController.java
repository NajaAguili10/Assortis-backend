package com.backend.assorttis.controller;

import com.backend.assorttis.dto.expert.ExpertDTO;
import com.backend.assorttis.dto.expert.ExpertSearchRequest;
import com.backend.assorttis.dto.expert.ExpertSearchResponse;
import com.backend.assorttis.service.ExpertService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/experts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ExpertController {

    private final ExpertService expertService;

    @GetMapping
    public ResponseEntity<List<ExpertDTO>> getAllExperts() {
        return ResponseEntity.ok(expertService.getAllExperts());
    }

    @GetMapping("/search")
    public ResponseEntity<ExpertSearchResponse> searchExperts(
            @ModelAttribute ExpertSearchRequest filters,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "updatedAt,desc") String sort
    ) {
        Pageable pageable = PageRequest.of(page, size, parseSort(sort));
        return ResponseEntity.ok(expertService.searchExperts(filters, pageable));
    }

    private Sort parseSort(String sort) {
        if (sort == null || sort.isBlank()) {
            return Sort.by(Sort.Direction.DESC, "updatedAt");
        }

        String[] parts = sort.split(",");
        String field = parts[0].isBlank() ? "updatedAt" : parts[0];
        Sort.Direction direction = parts.length > 1 && "asc".equalsIgnoreCase(parts[1])
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        if ("name".equalsIgnoreCase(field)) field = "fullName";
        if ("experience".equalsIgnoreCase(field)) field = "yearsExperience";
        if ("rating".equalsIgnoreCase(field)) field = "ratingAvg";
        if (!List.of("fullName", "yearsExperience", "ratingAvg", "updatedAt", "lastActiveAt").contains(field)) {
            field = "updatedAt";
        }
        return Sort.by(direction, field);
    }
}
