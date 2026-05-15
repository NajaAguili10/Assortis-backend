package com.backend.assorttis.service;

import com.backend.assorttis.dto.project.ProjectSavedSearchDTO;
import java.util.List;
import java.util.Map;

public interface ProjectSavedSearchService {
    List<ProjectSavedSearchDTO> getSavedSearches(Long userId);
    ProjectSavedSearchDTO saveSearch(Long userId, String name, Map<String, Object> payload);
    void deleteSavedSearch(Long id);
}
