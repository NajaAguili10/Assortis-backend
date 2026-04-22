package com.backend.assorttis.repository;

import com.backend.assorttis.entities.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TemplateRepository extends JpaRepository<Template, Long>, JpaSpecificationExecutor<Template> {
}
