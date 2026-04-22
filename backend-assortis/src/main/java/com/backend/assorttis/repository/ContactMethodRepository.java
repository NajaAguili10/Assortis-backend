package com.backend.assorttis.repository;

import com.backend.assorttis.entities.ContactMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContactMethodRepository
        extends JpaRepository<ContactMethod, Long>, JpaSpecificationExecutor<ContactMethod> {
}
