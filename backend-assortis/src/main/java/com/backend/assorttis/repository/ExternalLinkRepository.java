package com.backend.assorttis.repository;

import com.backend.assorttis.entities.ExternalLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExternalLinkRepository
        extends JpaRepository<ExternalLink, Long>, JpaSpecificationExecutor<ExternalLink> {
}
