package com.backend.assorttis.repository;

import com.backend.assorttis.entities.ConversationParticipant;
import com.backend.assorttis.entities.ConversationParticipantId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ConversationParticipantRepository
        extends JpaRepository<ConversationParticipant, ConversationParticipantId>,
        JpaSpecificationExecutor<ConversationParticipant> {
}
