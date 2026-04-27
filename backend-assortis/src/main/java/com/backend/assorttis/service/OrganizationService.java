package com.backend.assorttis.service;

import com.backend.assorttis.dto.organization.OrganizationDTO;
import com.backend.assorttis.dto.organization.OrganizationKPIsDTO;
import com.backend.assorttis.mappers.OrganizationMapper;
import com.backend.assorttis.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;

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

        return OrganizationKPIsDTO.builder()
                .totalOrganizations(total)
                .activeOrganizations(active)
                .verifiedOrganizations(verified)
                .countriesCovered(countries)
                .partnerships(0) // Mock for now or implement logic
                .newPartnerships(0)
                .invitations(0)
                .pendingInvitations(0)
                .build();
    }
}
