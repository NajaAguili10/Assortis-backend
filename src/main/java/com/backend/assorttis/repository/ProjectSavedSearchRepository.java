package com.backend.assorttis.repository;

import com.backend.assorttis.entities.ProjectSavedSearch;
import com.backend.assorttis.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectSavedSearchRepository extends JpaRepository<ProjectSavedSearch, Long> {
    List<ProjectSavedSearch> findByUserOrderByCreatedAtDesc(User user);
}
