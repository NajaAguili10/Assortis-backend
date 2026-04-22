package com.backend.assorttis.repository;

import com.backend.assorttis.entities.SupportTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SupportTicketRepository
        extends JpaRepository<SupportTicket, Long>, JpaSpecificationExecutor<SupportTicket> {
}
