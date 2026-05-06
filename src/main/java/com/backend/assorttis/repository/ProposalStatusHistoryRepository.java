package com.backend.assorttis.repository;

import com.backend.assorttis.entities.ProposalStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProposalStatusHistoryRepository
        extends JpaRepository<ProposalStatusHistory, Long>, JpaSpecificationExecutor<ProposalStatusHistory> {
}
