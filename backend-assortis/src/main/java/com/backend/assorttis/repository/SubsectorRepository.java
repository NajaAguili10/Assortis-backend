package com.backend.assorttis.repository;

import com.backend.assorttis.entities.Subsector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SubsectorRepository extends JpaRepository<Subsector, Long>, JpaSpecificationExecutor<Subsector> {
}
