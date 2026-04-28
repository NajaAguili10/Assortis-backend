package com.backend.assorttis.service;

import com.backend.assorttis.dto.organization.OrganizationDTO;
import com.backend.assorttis.dto.organization.OrganizationFiltersDataDTO;
import com.backend.assorttis.dto.organization.OrganizationKPIsDTO;
import com.backend.assorttis.mappers.OrganizationMapper;
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
    public OrganizationFiltersDataDTO getFiltersData() {
        List<String> types = organizationTypeRepository.findAll().stream().map(t -> t.getLabel()).collect(Collectors.toList());

        List<OrganizationDTO.SectorDTO> sectors = sectorRepository.findAll().stream().map(s -> {
            OrganizationDTO.SectorDTO dto = new OrganizationDTO.SectorDTO();
            dto.setId(s.getId());
            dto.setName(s.getName());
            dto.setCode(s.getCode());
            return dto;
        }).collect(Collectors.toList());

        List<OrganizationFiltersDataDTO.SubsectorDTO> subSectors = subsectorRepository.findAll().stream().map(s -> {
            OrganizationFiltersDataDTO.SubsectorDTO dto = new OrganizationFiltersDataDTO.SubsectorDTO();
            dto.setId(s.getId());
            dto.setName(s.getName());
            dto.setCode(s.getCode());
            dto.setSectorCode(s.getSector() != null ? s.getSector().getCode() : null);
            return dto;
        }).collect(Collectors.toList());

        List<OrganizationFiltersDataDTO.RegionDTO> regions = regionRepository.findAll().stream().map(r -> {
            OrganizationFiltersDataDTO.RegionDTO dto = new OrganizationFiltersDataDTO.RegionDTO();
            dto.setId(r.getId());
            dto.setName(r.getName());
            dto.setCode(r.getCode());
            return dto;
        }).collect(Collectors.toList());

        List<OrganizationFiltersDataDTO.CountryDTO> countries = countryRepository.findAll().stream().map(c -> {
            OrganizationFiltersDataDTO.CountryDTO dto = new OrganizationFiltersDataDTO.CountryDTO();
            dto.setId(c.getId());
            dto.setName(c.getName());
            dto.setCode(c.getCode());
            dto.setRegionCode(c.getRegionWorld()); // Using regionWorld as the link
            return dto;
        }).collect(Collectors.toList());

        return OrganizationFiltersDataDTO.builder()
                .type(types)
                .sectors(sectors)
                .subSectors(subSectors)
                .regions(regions)
                .countries(countries)
                .status(null)
                .build();
    }
}
