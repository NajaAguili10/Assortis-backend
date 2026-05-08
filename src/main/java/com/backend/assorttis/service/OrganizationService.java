package com.backend.assorttis.service;

import com.backend.assorttis.dto.location.CountryDTO;
import com.backend.assorttis.dto.organization.OrganizationDTO;

import com.backend.assorttis.dto.organization.OrganizationKPIsDTO;
import com.backend.assorttis.dto.organization.OrganizationSavedSearchDTO;
import com.backend.assorttis.entities.OrganizationSavedSearch;
import com.backend.assorttis.entities.User;
import com.backend.assorttis.mappers.CountryMapper;
import com.backend.assorttis.mappers.OrganizationMapper;
import com.backend.assorttis.mappers.SectorMapper;
import com.backend.assorttis.repository.*;
import com.backend.assorttis.repository.OrganizationRepository;
import com.backend.assorttis.repository.PartnershipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;
    private final OrganizationTypeRepository organizationTypeRepository;
    private final SectorRepository sectorRepository;
    private final SubsectorRepository subsectorRepository;
    private final RegionRepository regionRepository;
    private final CountryRepository countryRepository;

    private final OrganizationSavedSearchRepository savedSearchRepository;
    private final UserRepository userRepository;

    private final PartnershipRepository partnershipRepository;

    @Transactional(readOnly = true)
    public List<OrganizationDTO> getAllOrganizations() {
        return organizationRepository.findAll().stream()
                .map(organizationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrganizationKPIsDTO getKPIs() {
        long total = organizationRepository.count();
        long active = organizationRepository.countByIsActiveTrue();
        // Assuming verified means validation_status is 'verified' or validated is true
        long verified = organizationRepository.countByValidatedTrue();
        long countries = organizationRepository.countDistinctCountry();
        long partnerships = partnershipRepository.count();
        long newPartnerships = partnershipRepository.countByStatusIgnoreCase("pending");

        return OrganizationKPIsDTO.builder()
                .totalOrganizations(total)
                .activeOrganizations(active)
                .verifiedOrganizations(verified)
                .countriesCovered(countries)
                .partnerships(partnerships)
                .newPartnerships(newPartnerships)
                .invitations(0)
                .pendingInvitations(0)
                .build();
    }

    @Transactional(readOnly = true)
    public List<String> getSubscriptionSectors(Long organizationId) {
        return subscriptionSectorRepository.findById_OrganizationId(organizationId).stream()
                .map(oss -> oss.getSector().getCode())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<com.backend.assorttis.dto.sector.SectorDTO> getSubscriptionSectorDTOs(Long organizationId) {
        return subscriptionSectorRepository.findById_OrganizationId(organizationId).stream()
                .map(oss -> sectorMapper.toSectorDTO(oss.getSector()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CountryDTO> getSubscriptionCountryDTOs(Long organizationId) {
        return subscriptionCountryRepository.findById_OrganizationId(organizationId).stream()
                .map(osc -> countryMapper.toDTO(osc.getCountry()))
                .collect(Collectors.toList());
    }
}
