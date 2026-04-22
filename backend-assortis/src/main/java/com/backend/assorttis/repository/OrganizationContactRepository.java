package com.backend.assorttis.repository;

import com.backend.assorttis.entities.OrganizationContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrganizationContactRepository
        extends JpaRepository<OrganizationContact, Long>, JpaSpecificationExecutor<OrganizationContact> {
}
