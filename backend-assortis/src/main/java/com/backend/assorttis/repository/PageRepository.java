package com.backend.assorttis.repository;

import com.backend.assorttis.entities.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PageRepository extends JpaRepository<Page, Long>, JpaSpecificationExecutor<Page> {
}
