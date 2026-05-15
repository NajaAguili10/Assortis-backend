package com.backend.assorttis.repository;

import com.backend.assorttis.entities.Expert;
import com.backend.assorttis.entities.ExpertSubscriptionSector;
import com.backend.assorttis.entities.ExpertSubscriptionSectorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ExpertSubscriptionSectorRepository extends JpaRepository<ExpertSubscriptionSector, ExpertSubscriptionSectorId> {
    List<ExpertSubscriptionSector> findByExpert(Expert expert);
}
