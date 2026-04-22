package com.backend.assorttis.repository;

import com.backend.assorttis.entities.Neighborhood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NeighborhoodRepository
        extends JpaRepository<Neighborhood, Long>, JpaSpecificationExecutor<Neighborhood> {
}
