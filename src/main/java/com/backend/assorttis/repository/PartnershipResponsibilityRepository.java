package com.backend.assorttis.repository;

import com.backend.assorttis.entities.PartnershipResponsibility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PartnershipResponsibilityRepository
        extends JpaRepository<PartnershipResponsibility, Long>, JpaSpecificationExecutor<PartnershipResponsibility> {
}
