package com.backend.assorttis.repository;

import com.backend.assorttis.entities.TicketFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TicketFeedbackRepository
        extends JpaRepository<TicketFeedback, Long>, JpaSpecificationExecutor<TicketFeedback> {
}
