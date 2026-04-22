package com.backend.assorttis.repository;

import com.backend.assorttis.entities.OrganizationCountry;
import com.backend.assorttis.entities.OrganizationCountryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrganizationCountryRepository extends JpaRepository<OrganizationCountry, OrganizationCountryId>,
        JpaSpecificationExecutor<OrganizationCountry> {
}
