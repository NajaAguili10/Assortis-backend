package com.backend.assorttis.repository;

import com.backend.assorttis.entities.ContactCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContactCategoryRepository
        extends JpaRepository<ContactCategory, Long>, JpaSpecificationExecutor<ContactCategory> {
}
