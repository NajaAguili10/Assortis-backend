package com.backend.assorttis.repository;

import com.backend.assorttis.entities.ActivityFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ActivityFeedRepository
        extends JpaRepository<ActivityFeed, Long>, JpaSpecificationExecutor<ActivityFeed> {
}
