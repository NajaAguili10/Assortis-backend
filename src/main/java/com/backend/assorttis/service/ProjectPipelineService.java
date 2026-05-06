package com.backend.assorttis.service;

import com.backend.assorttis.dto.pipeline.ProjectPipelineEntryDTO;
import com.backend.assorttis.dto.project.PaginatedResponseDTO;
import com.backend.assorttis.entities.Tender;
import com.backend.assorttis.entities.TenderOrgInterest;
import com.backend.assorttis.entities.User;
import com.backend.assorttis.repository.OrganizationUserRepository;
import com.backend.assorttis.repository.TenderOrgInterestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectPipelineService {
    private final TenderOrgInterestRepository tenderOrgInterestRepository;
    private final OrganizationUserRepository organizationUserRepository;

    public PaginatedResponseDTO<ProjectPipelineEntryDTO> searchPipeline(
            String search,
            String stage,
            Pageable pageable,
            Authentication authentication,
            String userEmailHeader,
            String userRoleHeader
    ) {
        String userEmail = resolveUserEmail(authentication, userEmailHeader);
        boolean admin = isAdmin(authentication, userRoleHeader);
        String normalizedSearch = StringUtils.hasText(search) ? search.trim() : null;
        String normalizedStage = StringUtils.hasText(stage) && !"all".equalsIgnoreCase(stage) ? stage.trim() : null;

        log.info("Project pipeline search requested. userEmail={}, admin={}, search={}, stage={}, page={}, size={}",
                userEmail, admin, normalizedSearch, normalizedStage, pageable.getPageNumber(), pageable.getPageSize());

        Page<TenderOrgInterest> page;
        if (admin) {
            page = tenderOrgInterestRepository.searchAllPipeline(normalizedSearch, normalizedStage, pageable);
        } else if (StringUtils.hasText(userEmail)) {
            List<Long> organizationIds = organizationUserRepository.findActiveOrganizationIdsByUserEmail(userEmail);
            if (organizationIds.isEmpty()) {
                log.info("Project pipeline search returned no data because user has no active organizations. userEmail={}", userEmail);
                page = Page.empty(pageable);
            } else {
                page = tenderOrgInterestRepository.searchPipelineForOrganizations(organizationIds, normalizedSearch, normalizedStage, pageable);
            }
        } else {
            log.warn("Project pipeline search denied because no connected user was resolved.");
            page = Page.empty(pageable);
        }

        PaginatedResponseDTO<ProjectPipelineEntryDTO> response = new PaginatedResponseDTO<>();
        response.setData(page.getContent().stream().map(this::toDto).toList());
        PaginatedResponseDTO.PaginationMetaDTO meta = new PaginatedResponseDTO.PaginationMetaDTO();
        meta.setPage(page.getNumber());
        meta.setPageSize(page.getSize());
        meta.setTotalItems(page.getTotalElements());
        meta.setTotalPages(page.getTotalPages());
        meta.setHasNextPage(page.hasNext());
        meta.setHasPreviousPage(page.hasPrevious());
        response.setMeta(meta);

        log.info("Project pipeline search completed. userEmail={}, returned={}, total={}",
                userEmail, response.getData().size(), page.getTotalElements());
        return response;
    }

    private ProjectPipelineEntryDTO toDto(TenderOrgInterest interest) {
        Tender tender = interest.getTender();
        ProjectPipelineEntryDTO dto = new ProjectPipelineEntryDTO();
        dto.setTenderId(tender.getId());
        dto.setMemberOrganizationId(interest.getOrganization().getId());
        dto.setMemberOrganizationName(interest.getOrganization().getName());
        dto.setMemberUserNames(organizationUserRepository.findActiveUsersByOrganizationId(interest.getOrganization().getId()).stream()
                .map(organizationUser -> formatUserName(organizationUser.getUser()))
                .filter(StringUtils::hasText)
                .distinct()
                .toList());
        dto.setTenderTitle(tender.getTitle());
        dto.setTenderReference(tender.getReferenceCode());
        dto.setPublishedByOrganizationName(tender.getPublishedByOrganization() != null ? tender.getPublishedByOrganization().getName() : null);
        dto.setCountry(tender.getCountry() != null ? tender.getCountry().getCode() : null);
        dto.setDonor(tender.getDonor() != null ? tender.getDonor().getName() : null);
        dto.setStatus(tender.getStatus() != null ? tender.getStatus().getCode() : null);
        dto.setStage(interest.getPipelineStage());
        dto.setPriority(interest.getPriority());
        dto.setRole(interest.getRole());
        dto.setRoleSought(interest.getRoleSought());
        dto.setNotes(interest.getMessage());
        dto.setProgressPercent(interest.getProgressPercent());
        dto.setProbability(interest.getProbability());
        dto.setExpectedValue(interest.getExpectedValue() != null ? interest.getExpectedValue() : tender.getEstimatedBudget());
        dto.setCurrency(tender.getCurrency() != null ? tender.getCurrency() : "USD");
        dto.setMatchScore(interest.getMatchScore());
        dto.setAiRecommendation(interest.getAiRecommendation());
        dto.setDeadline(tender.getDeadline());
        dto.setAddedAt(interest.getCreatedAt() != null ? interest.getCreatedAt() : tender.getCreatedAt());
        dto.setUpdatedAt(interest.getUpdatedAt() != null ? interest.getUpdatedAt() : tender.getUpdatedAt());
        dto.setSectors(tender.getMainSector() != null ? List.of(tender.getMainSector().getCode()) : List.of());
        return dto;
    }

    private String formatUserName(User user) {
        if (user == null) {
            return null;
        }

        String fullName = List.of(user.getFirstName(), user.getLastName()).stream()
                .filter(StringUtils::hasText)
                .reduce((left, right) -> left + " " + right)
                .orElse(null);

        return StringUtils.hasText(fullName) ? fullName : user.getEmail();
    }

    private String resolveUserEmail(Authentication authentication, String userEmailHeader) {
        if (authentication != null && authentication.isAuthenticated() && StringUtils.hasText(authentication.getName())) {
            return authentication.getName();
        }
        return StringUtils.hasText(userEmailHeader) ? userEmailHeader.trim() : null;
    }

    private boolean isAdmin(Authentication authentication, String userRoleHeader) {
        if (StringUtils.hasText(userRoleHeader) && userRoleHeader.toLowerCase().contains("admin")) {
            return true;
        }

        return authentication != null
                && authentication.getAuthorities() != null
                && authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority -> authority != null && authority.toLowerCase().contains("admin"));
    }
}
