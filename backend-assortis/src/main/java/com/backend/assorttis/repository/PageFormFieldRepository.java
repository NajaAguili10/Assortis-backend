package com.backend.assorttis.repository;

import com.backend.assorttis.entities.PageFormField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PageFormFieldRepository
        extends JpaRepository<PageFormField, Long>, JpaSpecificationExecutor<PageFormField> {
}
