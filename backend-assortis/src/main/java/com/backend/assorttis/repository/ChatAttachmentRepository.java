package com.backend.assorttis.repository;

import com.backend.assorttis.entities.ChatAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ChatAttachmentRepository
        extends JpaRepository<ChatAttachment, Long>, JpaSpecificationExecutor<ChatAttachment> {
}
