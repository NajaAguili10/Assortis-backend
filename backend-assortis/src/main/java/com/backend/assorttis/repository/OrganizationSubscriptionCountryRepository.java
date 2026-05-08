package com.backend.assorttis.repository;

import com.backend.assorttis.entities.OrganizationSubscriptionCountry;
import com.backend.assorttis.entities.OrganizationSubscriptionCountryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationSubscriptionCountryRepository extends JpaRepository<OrganizationSubscriptionCountry, OrganizationSubscriptionCountryId> {
    java.util.List<OrganizationSubscriptionCountry> findById_OrganizationId(Long organizationId);
}
