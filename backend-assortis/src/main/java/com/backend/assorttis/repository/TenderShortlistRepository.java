package com.backend.assorttis.repository;

import com.backend.assorttis.entities.TenderShortlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TenderShortlistRepository
        extends JpaRepository<TenderShortlist, Long>, JpaSpecificationExecutor<TenderShortlist> {
}
