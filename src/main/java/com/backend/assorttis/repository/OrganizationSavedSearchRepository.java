package com.backend.assorttis.repository;

import com.backend.assorttis.entities.OrganizationSavedSearch;
import com.backend.assorttis.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrganizationSavedSearchRepository extends JpaRepository<OrganizationSavedSearch, Long> {
    List<OrganizationSavedSearch> findByUserOrderByCreatedAtDesc(User user);
}
