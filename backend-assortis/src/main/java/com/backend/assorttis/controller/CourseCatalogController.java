package com.backend.assorttis.controller;

import com.backend.assorttis.dto.training.CourseCatalogResponseDto;
import com.backend.assorttis.service.CourseCatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/training")
@RequiredArgsConstructor
public class CourseCatalogController {

    private final CourseCatalogService courseCatalogService;

    @GetMapping("/catalog")
    public ResponseEntity<List<CourseCatalogResponseDto>> getCatalogCourses() {
        return ResponseEntity.ok(courseCatalogService.getCatalogCourses());
    }
}
