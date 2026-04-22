package com.backend.assorttis.repository;

import com.backend.assorttis.entities.PartnershipCountry;
import com.backend.assorttis.entities.PartnershipCountryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PartnershipCountryRepository
        extends JpaRepository<PartnershipCountry, PartnershipCountryId>, JpaSpecificationExecutor<PartnershipCountry> {
}
