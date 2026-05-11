package com.backend.assorttis.service.impl;

import com.backend.assorttis.dto.project.ProjectSavedSearchDTO;
import com.backend.assorttis.entities.ProjectSavedSearch;
import com.backend.assorttis.entities.User;
import com.backend.assorttis.mappers.ProjectMapper;
import com.backend.assorttis.repository.ProjectSavedSearchRepository;
import com.backend.assorttis.repository.UserRepository;
import com.backend.assorttis.service.ProjectSavedSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProjectSavedSearchServiceImpl implements ProjectSavedSearchService {

    private final ProjectSavedSearchRepository repository;
    private final UserRepository userRepository;
    private final ProjectMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProjectSavedSearchDTO> getSavedSearches(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<ProjectSavedSearch> searches = repository.findByUserOrderByCreatedAtDesc(user);
        return mapper.toProjectSavedSearchDTOList(searches);
    }

    @Override
    @Transactional
    public ProjectSavedSearchDTO saveSearch(Long userId, String name, Map<String, Object> payload) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ProjectSavedSearch entity = new ProjectSavedSearch()
                .setUser(user)
                .setName(name)
                .setPayload(payload)
                .setCreatedAt(Instant.now());

        ProjectSavedSearch saved = repository.save(entity);
        return mapper.toProjectSavedSearchDTO(saved);
    }

    @Override
    @Transactional
    public void deleteSavedSearch(Long id) {
        repository.deleteById(id);
    }
}
