package com.backend.assorttis.repository;

import com.backend.assorttis.entities.ExpertSavedSearch;
import com.backend.assorttis.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpertSavedSearchRepository extends JpaRepository<ExpertSavedSearch, Long> {
    List<ExpertSavedSearch> findByUserOrderByCreatedAtDesc(User user);
}
