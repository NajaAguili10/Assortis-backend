package com.backend.assorttis.repository;

import com.backend.assorttis.entities.CtaCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CtaCardRepository extends JpaRepository<CtaCard, Long>, JpaSpecificationExecutor<CtaCard> {
}
