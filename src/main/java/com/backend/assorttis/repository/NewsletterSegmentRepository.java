package com.backend.assorttis.repository;

import com.backend.assorttis.entities.NewsletterSegment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NewsletterSegmentRepository
        extends JpaRepository<NewsletterSegment, Long>, JpaSpecificationExecutor<NewsletterSegment> {
}
