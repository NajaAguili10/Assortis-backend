package com.backend.assorttis.repository;

import com.backend.assorttis.entities.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DocumentTypeRepository
        extends JpaRepository<DocumentType, String>, JpaSpecificationExecutor<DocumentType> {
}
