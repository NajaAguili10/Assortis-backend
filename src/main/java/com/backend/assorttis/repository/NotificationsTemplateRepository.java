package com.backend.assorttis.repository;

import com.backend.assorttis.entities.NotificationsTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NotificationsTemplateRepository
        extends JpaRepository<NotificationsTemplate, Long>, JpaSpecificationExecutor<NotificationsTemplate> {
}
