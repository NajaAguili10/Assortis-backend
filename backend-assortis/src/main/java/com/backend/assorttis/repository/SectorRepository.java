package com.backend.assorttis.repository;

import com.backend.assorttis.entities.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface SectorRepository extends JpaRepository<Sector, Long>, JpaSpecificationExecutor<Sector> {

    Optional<Sector> findByCode(String code);

}
