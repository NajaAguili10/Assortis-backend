package com.backend.assorttis.repository;

import com.backend.assorttis.entities.TenderExpertInterest;
import com.backend.assorttis.entities.TenderExpertInterestId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TenderExpertInterestRepository extends JpaRepository<TenderExpertInterest, TenderExpertInterestId>,
        JpaSpecificationExecutor<TenderExpertInterest> {
}
