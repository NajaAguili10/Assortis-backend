package com.backend.assorttis.service;

import com.backend.assorttis.dto.project.*;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ProjectService {

    PaginatedResponseDTO<ProjectListDTO> getProjects(ProjectFiltersDTO filters, String sortBy, Pageable pageable);

    List<ProjectListDTO> getAllProjects();

    ProjectKPIsDTO getKPIs();




    ProjectDetailDTO getProjectById(Long id);
}

