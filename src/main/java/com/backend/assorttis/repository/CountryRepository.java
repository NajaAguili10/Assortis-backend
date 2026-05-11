package com.backend.assorttis.repository;

import com.backend.assorttis.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long>, JpaSpecificationExecutor<Country> {
    Optional<Country> findByCode(String code);
    Optional<Country> findByName(String name);
    Optional<Country> findByNameIgnoreCase(String name);
}
