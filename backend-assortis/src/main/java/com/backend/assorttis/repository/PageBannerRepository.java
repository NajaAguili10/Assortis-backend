package com.backend.assorttis.repository;

import com.backend.assorttis.entities.PageBanner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PageBannerRepository extends JpaRepository<PageBanner, Long>, JpaSpecificationExecutor<PageBanner> {
}
