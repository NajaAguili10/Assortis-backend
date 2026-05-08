package com.backend.assorttis.repository;

import com.backend.assorttis.entities.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PaymentHistoryRepository
        extends JpaRepository<PaymentHistory, Long>, JpaSpecificationExecutor<PaymentHistory> {
}
