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

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private final OrganizationUserRepository organizationUserRepository;

    private final PartnershipRepository partnershipRepository;
    private final OrganizationSubscriptionSectorRepository subscriptionSectorRepository;
    private final OrganizationSubscriptionCountryRepository subscriptionCountryRepository;

    private final SectorMapper sectorMapper;
    private final CountryMapper countryMapper;

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



    @Transactional(readOnly = true)
    public Long getOrganizationIdByUserId(Long userId) {
        return organizationUserRepository.findFirstByUserId(userId)
                .map(ou -> ou.getOrganization().getId())
                .orElse(null);
    }


    @Transactional(readOnly = true)
    public List<OrganizationSavedSearchDTO> getSavedSearches(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return savedSearchRepository.findByUserOrderByCreatedAtDesc(user).stream()
                .map(organizationMapper::toSavedSearchDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrganizationSavedSearchDTO saveSearch(Long userId, String name, Map<String, Object> payload) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        OrganizationSavedSearch savedSearch = new OrganizationSavedSearch()
                .setUser(user)
                .setName(name)
                .setPayload(payload)
                .setCreatedAt(Instant.now());
        return organizationMapper.toSavedSearchDTO(savedSearchRepository.save(savedSearch));
    }

    @Transactional
    public void deleteSavedSearch(Long id) {
        savedSearchRepository.deleteById(id);
    }
}
