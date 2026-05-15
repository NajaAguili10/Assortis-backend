package com.backend.assorttis.repository;

import com.backend.assorttis.entities.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RegionRepository extends JpaRepository<Region, Long>, JpaSpecificationExecutor<Region> {
    java.util.Optional<Region> findByCodeIgnoreCase(String code);
    java.util.Optional<Region> findByNameIgnoreCase(String name);
}
