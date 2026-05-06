package com.backend.assorttis.repository;

import com.backend.assorttis.entities.TenderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TenderHistoryRepository
        extends JpaRepository<TenderHistory, Long>, JpaSpecificationExecutor<TenderHistory> {
}
