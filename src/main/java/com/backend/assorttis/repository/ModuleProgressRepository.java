package com.backend.assorttis.repository;

import com.backend.assorttis.entities.ModuleProgress;
import com.backend.assorttis.entities.ModuleProgressId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ModuleProgressRepository
        extends JpaRepository<ModuleProgress, ModuleProgressId>, JpaSpecificationExecutor<ModuleProgress> {
}
