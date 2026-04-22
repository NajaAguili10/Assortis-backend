package com.backend.assorttis.repository;

import com.backend.assorttis.entities.UserRole;
import com.backend.assorttis.entities.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId>, JpaSpecificationExecutor<UserRole> {
}
