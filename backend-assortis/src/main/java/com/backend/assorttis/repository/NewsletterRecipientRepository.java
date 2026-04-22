package com.backend.assorttis.repository;

import com.backend.assorttis.entities.NewsletterRecipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NewsletterRecipientRepository
        extends JpaRepository<NewsletterRecipient, Long>, JpaSpecificationExecutor<NewsletterRecipient> {
}
