package com.backend.assorttis.repository;

import com.backend.assorttis.entities.ReadReceipt;
import com.backend.assorttis.entities.ReadReceiptId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReadReceiptRepository
        extends JpaRepository<ReadReceipt, ReadReceiptId>, JpaSpecificationExecutor<ReadReceipt> {
}
