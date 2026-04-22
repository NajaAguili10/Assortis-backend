package com.backend.assorttis.repository;

import com.backend.assorttis.entities.UsageStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UsageStatRepository extends JpaRepository<UsageStat, Long>, JpaSpecificationExecutor<UsageStat> {
}
