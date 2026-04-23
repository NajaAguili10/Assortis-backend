package com.backend.assorttis.repository;

import com.backend.assorttis.entities.Subsector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface SubsectorRepository extends JpaRepository<Subsector, Long>, JpaSpecificationExecutor<Subsector> {
    Optional<Subsector> findByCode(String subCode);
}
