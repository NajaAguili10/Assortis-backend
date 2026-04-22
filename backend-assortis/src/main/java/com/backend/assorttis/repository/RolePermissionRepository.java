package com.backend.assorttis.repository;

import com.backend.assorttis.entities.RolePermission;
import com.backend.assorttis.entities.RolePermissionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RolePermissionRepository
        extends JpaRepository<RolePermission, RolePermissionId>, JpaSpecificationExecutor<RolePermission> {
}
