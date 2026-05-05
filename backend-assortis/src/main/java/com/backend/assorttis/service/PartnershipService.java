package com.backend.assorttis.service;

import com.backend.assorttis.dto.partnership.PartnershipDTO;
import com.backend.assorttis.entities.Organization;
import com.backend.assorttis.entities.Partnership;
import com.backend.assorttis.repository.PartnershipProjectRepository;
import com.backend.assorttis.repository.PartnershipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartnershipService {

    private final PartnershipRepository partnershipRepository;
    private final PartnershipProjectRepository partnershipProjectRepository;

    @Transactional(readOnly = true)
    public List<PartnershipDTO> getAllPartnerships() {
        return partnershipRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    private PartnershipDTO toDTO(Partnership partnership) {
        Organization organization = partnership.getOrganization();
        Organization partnerOrganization = partnership.getPartnerOrganization();

        return PartnershipDTO.builder()
                .id(partnership.getId())
                .organizationName(organization != null ? organization.getName() : null)
                .organizationType(organization != null ? organization.getType() : null)
                .partnerOrganizationName(partnerOrganization != null ? partnerOrganization.getName() : null)
                .partnerOrganizationType(partnerOrganization != null ? partnerOrganization.getType() : null)
                .status(partnership.getStatus())
                .type(partnership.getType())
                .startDate(partnership.getStartDate())
                .endDate(partnership.getEndDate())
                .createdAt(partnership.getCreatedAt())
                .description(partnership.getDescription())
                .region(resolveRegion(partnerOrganization, organization))
                .sector(resolveSector(partnerOrganization, organization))
                .projectsCount(partnershipProjectRepository.countByPartnershipId(partnership.getId()))
                .build();
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
}
