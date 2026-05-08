package com.backend.assorttis.repository;

import com.backend.assorttis.entities.ExpertSkill;
import com.backend.assorttis.entities.ExpertSkillId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExpertSkillRepository
        extends JpaRepository<ExpertSkill, ExpertSkillId>, JpaSpecificationExecutor<ExpertSkill> {
}
