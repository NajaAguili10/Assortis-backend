package com.backend.assorttis.service;

import com.backend.assorttis.dto.organization.CurrentOrganizationUpdateRequest;
import com.backend.assorttis.dto.organization.OrganizationDTO;
import com.backend.assorttis.dto.organization.OrganizationKPIsDTO;
import com.backend.assorttis.entities.City;
import com.backend.assorttis.entities.Country;
import com.backend.assorttis.entities.Language;
import com.backend.assorttis.entities.Organization;
import com.backend.assorttis.entities.OrganizationLanguage;
import com.backend.assorttis.entities.OrganizationLanguageId;
import com.backend.assorttis.entities.OrganizationSector;
import com.backend.assorttis.entities.OrganizationSectorId;
import com.backend.assorttis.entities.OrganizationSubsector;
import com.backend.assorttis.entities.OrganizationSubsectorId;
import com.backend.assorttis.entities.OrganizationServiceId;
import com.backend.assorttis.mappers.OrganizationMapper;
import com.backend.assorttis.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

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
    private final CityRepository cityRepository;
    private final LanguageRepository languageRepository;
    private final ServiceRepository serviceRepository;
    private final OrganizationSectorRepository organizationSectorRepository;
    private final OrganizationSubsectorRepository organizationSubsectorRepository;
    private final OrganizationLanguageRepository organizationLanguageRepository;
    private final OrganizationServiceRepository organizationServiceRepository;
    private final OrganizationUserRepository organizationUserRepository;
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
    public OrganizationDTO getCurrentOrganization(String userEmail) {
        return organizationMapper.toDTO(resolveCurrentOrganization(userEmail));
    }

    @Transactional
    public OrganizationDTO updateCurrentOrganization(String userEmail, CurrentOrganizationUpdateRequest request) {
        Organization organization = resolveCurrentOrganization(userEmail);

        organization.setName(defaultIfBlank(request.getName(), organization.getName()));
        organization.setAcronym(blankToNull(request.getAcronym()));
        organization.setType(blankToNull(request.getType()));
        organization.setLegalName(blankToNull(request.getLegalName()));
        organization.setRegistrationNumber(blankToNull(request.getRegistrationNumber()));
        organization.setYearFounded(request.getYearFounded());
        organization.setDescription(blankToNull(request.getDescription()));
        organization.setContactEmail(blankToNull(request.getContactEmail()));
        organization.setContactPhone(blankToNull(request.getContactPhone()));
        organization.setWebsite(blankToNull(request.getWebsite()));
        organization.setAddress(blankToNull(request.getAddress()));
        organization.setPostalCode(blankToNull(request.getPostalCode()));
        organization.setOperatingRegionsRaw(joinValues(request.getOperatingRegions()));
        organization.setRegion(resolvePrimaryRegion(request));
        organization.setEmployeesCount(request.getEmployeesCount());
        organization.setAnnualTurnover(request.getAnnualTurnover() != null ? request.getAnnualTurnover() : BigDecimal.ZERO);
        organization.setProfileProjectsCompleted(request.getProjectsCompleted());
        organization.setCityNameOverride(blankToNull(request.getCity()));
        organization.setCountryNameOverride(blankToNull(request.getCountry()));
        organization.setCity(resolveCity(request.getCity()));
        organization.setCountry(resolveCountry(request.getCountry()));

        Organization persistedOrganization = organizationRepository.save(organization);

        replaceOrganizationSectors(persistedOrganization, request.getSectors());
        replaceOrganizationSubsectors(persistedOrganization, request.getSubsectors());
        replaceOrganizationLanguages(persistedOrganization, request.getLanguages());
        replaceOrganizationServices(persistedOrganization, request.getServices());

        return organizationMapper.toDTO(persistedOrganization);
    }

    private Organization resolveCurrentOrganization(String userEmail) {
        if (!StringUtils.hasText(userEmail)) {
            throw new ResponseStatusException(UNAUTHORIZED, "Authenticated user not found");
        }

        List<Long> organizationIds = organizationUserRepository.findActiveOrganizationIdsByUserEmail(userEmail.trim());
        if (organizationIds.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "No active organization found for user");
        }

        return organizationRepository.findById(organizationIds.get(0))
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Organization not found"));
    }

    private Country resolveCountry(String countryName) {
        if (!StringUtils.hasText(countryName)) {
            return null;
        }

        return countryRepository.findByNameIgnoreCase(countryName.trim())
                .or(() -> countryRepository.findByCode(countryName.trim().toUpperCase(Locale.ROOT)))
                .orElse(null);
    }

    private City resolveCity(String cityName) {
        if (!StringUtils.hasText(cityName)) {
            return null;
        }

        return cityRepository.findByNameIgnoreCase(cityName.trim())
                .orElseGet(() -> cityRepository.findByName(cityName.trim()).orElse(null));
    }

    private void replaceOrganizationSectors(Organization organization, List<String> sectorCodes) {
        organizationSectorRepository.deleteAll(organizationSectorRepository.findByOrganizationId(organization.getId()));

        if (sectorCodes == null) {
            return;
        }

        sectorCodes.stream()
                .filter(StringUtils::hasText)
                .map(code -> sectorRepository.findByCode(code.trim()).orElse(null))
                .filter(java.util.Objects::nonNull)
                .distinct()
                .forEach(sector -> {
                    OrganizationSector organizationSector = new OrganizationSector();
                    organizationSector.setId(new OrganizationSectorId()
                            .setOrganizationId(organization.getId())
                            .setSectorId(sector.getId()));
                    organizationSector.setOrganization(organization);
                    organizationSector.setSector(sector);
                    organizationSectorRepository.save(organizationSector);
                });
    }

    private void replaceOrganizationSubsectors(Organization organization, List<String> subsectorCodes) {
        organizationSubsectorRepository.deleteAll(organizationSubsectorRepository.findByOrganizationId(organization.getId()));

        if (subsectorCodes == null) {
            return;
        }

        subsectorCodes.stream()
                .filter(StringUtils::hasText)
                .map(code -> subsectorRepository.findByCode(code.trim()).orElse(null))
                .filter(java.util.Objects::nonNull)
                .distinct()
                .forEach(subsector -> {
                    OrganizationSubsector organizationSubsector = new OrganizationSubsector();
                    organizationSubsector.setId(new OrganizationSubsectorId()
                            .setOrganizationId(organization.getId())
                            .setSubsectorId(subsector.getId()));
                    organizationSubsector.setOrganization(organization);
                    organizationSubsector.setSubsector(subsector);
                    organizationSubsectorRepository.save(organizationSubsector);
                });
    }

    private void replaceOrganizationLanguages(Organization organization, List<String> languageNames) {
        organizationLanguageRepository.deleteAll(organizationLanguageRepository.findByOrganizationId(organization.getId()));

        if (languageNames == null) {
            return;
        }

        languageNames.stream()
                .filter(StringUtils::hasText)
                .map(name -> languageRepository.findByNameIgnoreCase(name.trim()).orElse(null))
                .filter(java.util.Objects::nonNull)
                .distinct()
                .forEach(language -> {
                    OrganizationLanguage organizationLanguage = new OrganizationLanguage();
                    organizationLanguage.setId(new OrganizationLanguageId()
                            .setOrganizationId(organization.getId())
                            .setLanguageCode(language.getCode()));
                    organizationLanguage.setOrganization(organization);
                    organizationLanguage.setLanguageCode(language);
                    organizationLanguageRepository.save(organizationLanguage);
                });
    }

    private void replaceOrganizationServices(Organization organization, List<String> serviceLabels) {
        organizationServiceRepository.deleteAll(organizationServiceRepository.findByOrganizationId(organization.getId()));

        if (serviceLabels == null) {
            return;
        }

        serviceLabels.stream()
                .filter(StringUtils::hasText)
                .map(label -> serviceRepository.findByLabelIgnoreCase(label.trim()).orElse(null))
                .filter(java.util.Objects::nonNull)
                .distinct()
                .forEach(service -> {
                    com.backend.assorttis.entities.OrganizationService organizationService =
                            new com.backend.assorttis.entities.OrganizationService();
                    organizationService.setId(new OrganizationServiceId()
                            .setOrganizationId(organization.getId())
                            .setServiceId(service.getId()));
                    organizationService.setOrganization(organization);
                    organizationService.setService(service);
                    organizationServiceRepository.save(organizationService);
                });
    }

    private String blankToNull(String value) {
        return StringUtils.hasText(value) ? value.trim() : null;
    }

    private String resolvePrimaryRegion(CurrentOrganizationUpdateRequest request) {
        if (request.getOperatingRegions() != null) {
            return request.getOperatingRegions().stream()
                    .filter(StringUtils::hasText)
                    .map(String::trim)
                    .findFirst()
                    .orElseGet(() -> blankToNull(request.getRegion()));
        }

        return blankToNull(request.getRegion());
    }

    private String joinValues(List<String> values) {
        if (values == null) {
            return null;
        }

        List<String> normalized = values.stream()
                .filter(StringUtils::hasText)
                .map(String::trim)
                .distinct()
                .toList();

        return normalized.isEmpty() ? null : String.join(",", normalized);
    }

    private String defaultIfBlank(String value, String fallback) {
        return StringUtils.hasText(value) ? value.trim() : fallback;
    }

}
