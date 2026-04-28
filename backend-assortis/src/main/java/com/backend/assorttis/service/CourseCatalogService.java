package com.backend.assorttis.service;

import com.backend.assorttis.dto.training.CourseCatalogResponseDto;

import java.util.List;

public interface CourseCatalogService {

    List<CourseCatalogResponseDto> getCatalogCourses();
}
