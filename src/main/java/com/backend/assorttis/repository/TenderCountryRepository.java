package com.backend.assorttis.repository;

import com.backend.assorttis.entities.TenderCountry;
import com.backend.assorttis.entities.TenderCountryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TenderCountryRepository
        extends JpaRepository<TenderCountry, TenderCountryId>, JpaSpecificationExecutor<TenderCountry> {
}
