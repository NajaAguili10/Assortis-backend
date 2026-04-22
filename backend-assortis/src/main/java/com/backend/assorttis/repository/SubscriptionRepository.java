package com.backend.assorttis.repository;

import com.backend.assorttis.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SubscriptionRepository
        extends JpaRepository<Subscription, Long>, JpaSpecificationExecutor<Subscription> {
}
