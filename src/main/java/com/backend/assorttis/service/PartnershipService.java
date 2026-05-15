package com.backend.assorttis.service;

import com.backend.assorttis.dto.partnership.PartnershipDTO;
import com.backend.assorttis.entities.Organization;
import com.backend.assorttis.entities.OrganizationUser;
import com.backend.assorttis.entities.Partnership;
import com.backend.assorttis.repository.OrganizationUserRepository;
import com.backend.assorttis.repository.PartnershipProjectRepository;
import com.backend.assorttis.repository.PartnershipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PartnershipService {

    private final PartnershipRepository partnershipRepository;
    private final PartnershipProjectRepository partnershipProjectRepository;
    private final OrganizationUserRepository organizationUserRepository;

    @Transactional(readOnly = true)
    public List<PartnershipDTO> getAllPartnerships() {
        return partnershipRepository.findAll().stream()
                .map(partnership -> toDTO(partnership, null))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PartnershipDTO> getCurrentOrganizationPartnerships(Long userId) {
        Organization currentOrganization = resolveCurrentOrganization(userId);

        return partnershipRepository.findByOrganizationIdOrPartnerOrganizationId(currentOrganization.getId()).stream()
                .map(partnership -> toDTO(partnership, currentOrganization.getId()))
                .toList();
    }

    private PartnershipDTO toDTO(Partnership partnership, Long currentOrganizationId) {
        Organization organization = partnership.getOrganization();
        Organization partnerOrganization = partnership.getPartnerOrganization();
        Organization displayOrganization = organization;
        Organization counterpartOrganization = partnerOrganization;

        if (currentOrganizationId != null
                && partnerOrganization != null
                && currentOrganizationId.equals(partnerOrganization.getId())) {
            displayOrganization = partnerOrganization;
            counterpartOrganization = organization;
        }

        return PartnershipDTO.builder()
                .id(partnership.getId())
                .organizationName(displayOrganization != null ? displayOrganization.getName() : null)
                .organizationType(displayOrganization != null ? displayOrganization.getType() : null)
                .partnerOrganizationName(counterpartOrganization != null ? counterpartOrganization.getName() : null)
                .partnerOrganizationType(counterpartOrganization != null ? counterpartOrganization.getType() : null)
                .status(partnership.getStatus())
                .type(partnership.getType())
                .startDate(partnership.getStartDate())
                .endDate(partnership.getEndDate())
                .createdAt(partnership.getCreatedAt())
                .description(partnership.getDescription())
                .region(resolveRegion(counterpartOrganization, displayOrganization))
                .sector(resolveSector(counterpartOrganization, displayOrganization))
                .projectsCount(partnershipProjectRepository.countByPartnershipId(partnership.getId()))
                .build();
    }

    private Organization resolveCurrentOrganization(Long userId) {
        return organizationUserRepository.findMembershipsByUserId(userId).stream()
                .filter(membership -> !"inactive".equalsIgnoreCase(nullToEmpty(membership.getMembershipStatus())))
                .map(OrganizationUser::getOrganization)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "No organization found for user"));
    }

    private String resolveRegion(Organization primaryOrganization, Organization fallbackOrganization) {
        String primaryRegion = getRegion(primaryOrganization);
        if (primaryRegion != null && !primaryRegion.isBlank()) {
            return primaryRegion;
        }

        String fallbackRegion = getRegion(fallbackOrganization);
        return fallbackRegion != null ? fallbackRegion : "";
    }

    private String getRegion(Organization organization) {
        if (organization == null) {
            return null;
        }

        if (organization.getRegion() != null && !organization.getRegion().isBlank()) {
            return organization.getRegion();
        }

        if (organization.getCountry() != null && organization.getCountry().getRegionWorld() != null) {
            return organization.getCountry().getRegionWorld();
        }

        if (organization.getCountry() != null && organization.getCountry().getName() != null) {
            return organization.getCountry().getName();
        }

        return null;
    }

    private String resolveSector(Organization primaryOrganization, Organization fallbackOrganization) {
        String primarySector = getSector(primaryOrganization);
        if (primarySector != null && !primarySector.isBlank()) {
            return primarySector;
        }

        String fallbackSector = getSector(fallbackOrganization);
        return fallbackSector != null ? fallbackSector : "";
    }

    private String getSector(Organization organization) {
        if (organization == null || organization.getMainSector() == null) {
            return null;
        }

        if (organization.getMainSector().getName() != null && !organization.getMainSector().getName().isBlank()) {
            return organization.getMainSector().getName();
        }

        return organization.getMainSector().getCode();
    }

    private String nullToEmpty(String value) {
        return value == null ? "" : value;
    }
}