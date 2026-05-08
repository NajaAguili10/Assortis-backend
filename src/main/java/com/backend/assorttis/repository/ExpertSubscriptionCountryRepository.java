package com.backend.assorttis.repository;

import com.backend.assorttis.entities.ExpertSubscriptionCountry;
import com.backend.assorttis.entities.ExpertSubscriptionCountryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpertSubscriptionCountryRepository extends JpaRepository<ExpertSubscriptionCountry, ExpertSubscriptionCountryId> {
}
