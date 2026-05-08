package com.backend.assorttis.repository;

import com.backend.assorttis.entities.AssistanceCollaboration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AssistanceCollaborationRepository
        extends JpaRepository<AssistanceCollaboration, Long>, JpaSpecificationExecutor<AssistanceCollaboration> {
}
