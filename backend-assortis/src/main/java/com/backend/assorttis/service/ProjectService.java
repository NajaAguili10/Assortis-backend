package com.backend.assorttis.service;


import com.backend.assorttis.dto.project.*;
import org.springframework.data.domain.Pageable;

public interface ProjectService {

    PaginatedResponseDTO<ProjectListDTO> getProjects(ProjectFiltersDTO filters, String sortBy, Pageable pageable);

    ProjectKPIsDTO getKPIs();

    ProjectDetailDTO getProjectById(Long id);
}
