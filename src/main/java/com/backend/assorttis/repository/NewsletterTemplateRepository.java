package com.backend.assorttis.repository;

import com.backend.assorttis.entities.NewsletterTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NewsletterTemplateRepository
        extends JpaRepository<NewsletterTemplate, Long>, JpaSpecificationExecutor<NewsletterTemplate> {
}
