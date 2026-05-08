package com.backend.assorttis.repository;

import com.backend.assorttis.entities.BookmarkedOrganization;
import com.backend.assorttis.entities.BookmarkedOrganizationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkedOrganizationRepository extends JpaRepository<BookmarkedOrganization, BookmarkedOrganizationId> {
    List<BookmarkedOrganization> findByIdOrganizationId(Long organizationId);
}
