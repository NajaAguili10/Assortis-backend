package com.backend.assorttis.repository;

import com.backend.assorttis.entities.ExpertAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExpertAvailabilityRepository
        extends JpaRepository<ExpertAvailability, Long>, JpaSpecificationExecutor<ExpertAvailability> {
}
