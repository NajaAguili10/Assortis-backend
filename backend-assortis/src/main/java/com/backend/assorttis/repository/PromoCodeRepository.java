package com.backend.assorttis.repository;

import com.backend.assorttis.entities.PromoCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PromoCodeRepository extends JpaRepository<PromoCode, Long>, JpaSpecificationExecutor<PromoCode> {
}
