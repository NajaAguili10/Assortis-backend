package com.backend.assorttis.repository;

import com.backend.assorttis.entities.NewsletterCampaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NewsletterCampaignRepository
        extends JpaRepository<NewsletterCampaign, Long>, JpaSpecificationExecutor<NewsletterCampaign> {
}
