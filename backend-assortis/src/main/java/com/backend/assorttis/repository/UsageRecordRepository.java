package com.backend.assorttis.repository;

import com.backend.assorttis.entities.UsageRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UsageRecordRepository extends JpaRepository<UsageRecord, Long>, JpaSpecificationExecutor<UsageRecord> {
}
