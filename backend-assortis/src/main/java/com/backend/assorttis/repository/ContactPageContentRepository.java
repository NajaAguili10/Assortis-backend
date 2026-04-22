package com.backend.assorttis.repository;

import com.backend.assorttis.entities.ContactPageContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContactPageContentRepository
        extends JpaRepository<ContactPageContent, Integer>, JpaSpecificationExecutor<ContactPageContent> {
}
