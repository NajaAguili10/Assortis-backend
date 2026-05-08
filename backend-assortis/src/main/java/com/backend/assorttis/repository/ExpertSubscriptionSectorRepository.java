package com.backend.assorttis.repository;

import com.backend.assorttis.entities.ExpertSubscriptionSector;
import com.backend.assorttis.entities.ExpertSubscriptionSectorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpertSubscriptionSectorRepository extends JpaRepository<ExpertSubscriptionSector, ExpertSubscriptionSectorId> {
}
