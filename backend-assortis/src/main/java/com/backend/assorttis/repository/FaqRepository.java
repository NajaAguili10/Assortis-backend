package com.backend.assorttis.repository;

import com.backend.assorttis.entities.Faq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FaqRepository extends JpaRepository<Faq, Long>, JpaSpecificationExecutor<Faq> {
}
