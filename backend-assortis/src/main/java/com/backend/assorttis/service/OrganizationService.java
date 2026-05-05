package com.backend.assorttis.service;

import com.backend.assorttis.dto.organization.OrganizationDTO;
import com.backend.assorttis.dto.organization.OrganizationFiltersDataDTO;
import com.backend.assorttis.dto.organization.OrganizationKPIsDTO;
import com.backend.assorttis.dto.organization.OrganizationSavedSearchDTO;
import com.backend.assorttis.entities.OrganizationSavedSearch;
import com.backend.assorttis.entities.User;
import com.backend.assorttis.mappers.OrganizationMapper;
import com.backend.assorttis.repository.*;
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
        long verified = organizationRepository.countByValidatedTrue();
        long countries = organizationRepository.countDistinctCountry();

        return OrganizationKPIsDTO.builder()
                .totalOrganizations(total)
                .activeOrganizations(active)
                .verifiedOrganizations(verified)
                .countriesCovered(countries)
                .partnerships(0)
                .newPartnerships(0)
                .invitations(0)
                .pendingInvitations(0)
                .build();
    }


}
