package com.backend.assorttis.service;

import com.backend.assorttis.dto.organization.OrganizationMatchingDossierDTO;
import com.backend.assorttis.dto.organization.OrganizationMatchingDossierRequest;
import com.backend.assorttis.dto.organization.OrganizationMatchingStatsDTO;
import com.backend.assorttis.entities.Organization;
import com.backend.assorttis.entities.OrganizationMatchingDossier;
import com.backend.assorttis.entities.OrganizationUser;
import com.backend.assorttis.entities.User;
import com.backend.assorttis.repository.OrganizationMatchingDossierRepository;
import com.backend.assorttis.repository.OrganizationUserRepository;
import com.backend.assorttis.repository.TeamMemberRepository;
import com.backend.assorttis.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class OrganizationMatchingDossierService {
    private final OrganizationMatchingDossierRepository dossierRepository;
    private final OrganizationUserRepository organizationUserRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<OrganizationMatchingDossierDTO> getCurrentOrganizationDossiers(Long userId) {
        Organization organization = resolveCurrentOrganization(userId);
        return dossierRepository.findByOrganizationIdOrderByCreatedAtDesc(organization.getId()).stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public OrganizationMatchingDossierDTO getCurrentOrganizationDossier(Long userId, Long dossierId) {
        Organization organization = resolveCurrentOrganization(userId);
        return toDTO(findScopedDossier(dossierId, organization.getId()));
    }

    @Transactional(readOnly = true)
    public OrganizationMatchingStatsDTO getCurrentOrganizationStats(Long userId) {
        Organization organization = resolveCurrentOrganization(userId);
        List<OrganizationMatchingDossier> dossiers =
                dossierRepository.findByOrganizationIdOrderByCreatedAtDesc(organization.getId());

        Optional<OrganizationMatchingDossier> latestDossier = dossiers.stream()
                .max((left, right) -> resolveLastUpdated(left).compareTo(resolveLastUpdated(right)));

        long totalSaved = dossiers.size();
        Instant monthAgo = Instant.now().minus(30, ChronoUnit.DAYS);
        long thisMonth = dossiers.stream()
                .map(OrganizationMatchingDossier::getCreatedAt)
                .filter(Objects::nonNull)
                .filter(createdAt -> !createdAt.isBefore(monthAgo))
                .count();
        long dossierHighMatches = dossiers.stream()
                .mapToLong(dossier -> countHighMatches(dossier.getResults()))
                .sum();

        return OrganizationMatchingStatsDTO.builder()
                .available(latestDossier.map(OrganizationMatchingDossier::getTotalOrganizations).orElse(0))
                .highMatches(latestDossier.map(dossier -> countHighMatches(dossier.getResults())).orElse(0L))
                .avgScore(latestDossier.map(OrganizationMatchingDossier::getAvgScore).orElse(0))
                .totalSaved(totalSaved)
                .thisMonth(thisMonth)
                .dossierHighMatches(dossierHighMatches)
                .build();
    }

    @Transactional
    public OrganizationMatchingDossierDTO createCurrentOrganizationDossier(
            Long userId,
            OrganizationMatchingDossierRequest request
    ) {
        Organization organization = resolveCurrentOrganization(userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found"));
        Instant now = Instant.now();

        OrganizationMatchingDossier dossier = new OrganizationMatchingDossier()
                .setOrganization(organization)
                .setCreatedByUser(user)
                .setName(resolveName(request))
                .setAvgScore(request.getAvgScore())
                .setTotalOrganizations(resolveTotalOrganizations(request))
                .setResults(request.getResults() == null ? List.of() : request.getResults())
                .setFilters(request.getFilters())
                .setCreatedAt(now)
                .setUpdatedAt(now);

        return toDTO(dossierRepository.save(dossier));
    }

    @Transactional
    public OrganizationMatchingDossierDTO updateCurrentOrganizationDossier(
            Long userId,
            Long dossierId,
            OrganizationMatchingDossierRequest request
    ) {
        Organization organization = resolveCurrentOrganization(userId);
        OrganizationMatchingDossier dossier = findScopedDossier(dossierId, organization.getId());

        dossier
                .setName(resolveName(request))
                .setAvgScore(request.getAvgScore())
                .setTotalOrganizations(resolveTotalOrganizations(request))
                .setResults(request.getResults() == null ? List.of() : request.getResults())
                .setFilters(request.getFilters())
                .setUpdatedAt(Instant.now());

        return toDTO(dossierRepository.save(dossier));
    }

    @Transactional
    public void deleteCurrentOrganizationDossier(Long userId, Long dossierId) {
        Organization organization = resolveCurrentOrganization(userId);
        OrganizationMatchingDossier dossier = findScopedDossier(dossierId, organization.getId());
        dossierRepository.delete(dossier);
    }

    private OrganizationMatchingDossier findScopedDossier(Long dossierId, Long organizationId) {
        return dossierRepository.findByIdAndOrganizationId(dossierId, organizationId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Matching dossier not found"));
    }

    private Organization resolveCurrentOrganization(Long userId) {
        return organizationUserRepository.findMembershipsByUserId(userId).stream()
                .filter(membership -> !"inactive".equalsIgnoreCase(nullToEmpty(membership.getMembershipStatus())))
                .map(OrganizationUser::getOrganization)
                .findFirst()
                .orElseGet(() -> teamMemberRepository.findMembershipsByUserId(userId).stream()
                        .map(teamMember -> teamMember.getTeam().getOrganization())
                        .findFirst()
                        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "No organization found for user")));
    }

    private String resolveName(OrganizationMatchingDossierRequest request) {
        return StringUtils.hasText(request.getName()) ? request.getName().trim() : "Matching Dossier";
    }

    private Integer resolveTotalOrganizations(OrganizationMatchingDossierRequest request) {
        if (request.getTotalOrganizations() != null) {
            return request.getTotalOrganizations();
        }
        return request.getResults() == null ? 0 : request.getResults().size();
    }

    private OrganizationMatchingDossierDTO toDTO(OrganizationMatchingDossier dossier) {
        OrganizationMatchingDossierDTO dto = new OrganizationMatchingDossierDTO();
        dto.setId(dossier.getId());
        dto.setOrganizationId(dossier.getOrganization().getId());
        dto.setCreatedByUserId(dossier.getCreatedByUser().getId());
        dto.setName(dossier.getName());
        dto.setAvgScore(dossier.getAvgScore());
        dto.setTotalOrganizations(dossier.getTotalOrganizations());
        dto.setResults(dossier.getResults());
        dto.setFilters(dossier.getFilters());
        dto.setCreatedAt(dossier.getCreatedAt());
        dto.setUpdatedAt(dossier.getUpdatedAt());
        return dto;
    }

    private String nullToEmpty(String value) {
        return value == null ? "" : value;
    }

    private Instant resolveLastUpdated(OrganizationMatchingDossier dossier) {
        return dossier.getUpdatedAt() != null
                ? dossier.getUpdatedAt()
                : dossier.getCreatedAt() != null ? dossier.getCreatedAt() : Instant.EPOCH;
    }

    private long countHighMatches(List<Map<String, Object>> results) {
        if (results == null) {
            return 0;
        }

        return results.stream()
                .map(result -> result == null ? null : result.get("matchScore"))
                .mapToLong(this::toLong)
                .filter(score -> score >= 80)
                .count();
    }

    private long toLong(Object value) {
        if (value instanceof Number number) {
            return number.longValue();
        }
        if (value instanceof String stringValue) {
            try {
                return Long.parseLong(stringValue);
            } catch (NumberFormatException ignored) {
                return 0;
            }
        }
        return 0;
    }
}
