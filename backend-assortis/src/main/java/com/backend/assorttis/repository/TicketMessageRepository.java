package com.backend.assorttis.repository;

import com.backend.assorttis.entities.TicketMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TicketMessageRepository
        extends JpaRepository<TicketMessage, Long>, JpaSpecificationExecutor<TicketMessage> {
}
