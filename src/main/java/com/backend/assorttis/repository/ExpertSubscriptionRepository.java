package com.backend.assorttis.repository;

import com.backend.assorttis.entities.ExpertSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExpertSubscriptionRepository
        extends JpaRepository<ExpertSubscription, Long>, JpaSpecificationExecutor<ExpertSubscription> {
}
