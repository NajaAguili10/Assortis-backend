package com.backend.assorttis.service;

import com.backend.assorttis.entities.BookmarkedOrganization;
import com.backend.assorttis.entities.BookmarkedOrganizationId;
import com.backend.assorttis.entities.Organization;
import com.backend.assorttis.repository.BookmarkedOrganizationRepository;
import com.backend.assorttis.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookmarkedOrganizationService {

    private final BookmarkedOrganizationRepository bookmarkRepository;
    private final OrganizationRepository organizationRepository;

    @Transactional
    public boolean toggleBookmark(Long currentOrgId, Long targetOrgId) {
        BookmarkedOrganizationId id = new BookmarkedOrganizationId();
        id.setOrganizationId(currentOrgId);
        id.setBookmarkedOrgId(targetOrgId);

        if (bookmarkRepository.existsById(id)) {
            bookmarkRepository.deleteById(id);
            return false; // Removed
        } else {
            Organization currentOrg = organizationRepository.findById(currentOrgId)
                    .orElseThrow(() -> new RuntimeException("Current organization not found"));
            Organization targetOrg = organizationRepository.findById(targetOrgId)
                    .orElseThrow(() -> new RuntimeException("Target organization to bookmark not found"));

            BookmarkedOrganization bookmark = new BookmarkedOrganization();
            bookmark.setId(id);
            bookmark.setOrganization(currentOrg);
            bookmark.setBookmarkedOrg(targetOrg);
            bookmark.setCreatedAt(Instant.now());

            bookmarkRepository.save(bookmark);
            return true; // Added
        }
    }

    @Transactional(readOnly = true)
    public List<Long> getBookmarkedOrganizationIds(Long currentOrgId) {
        return bookmarkRepository.findByIdOrganizationId(currentOrgId).stream()
                .map(b -> b.getId().getBookmarkedOrgId())
                .collect(Collectors.toList());
    }
}
