package com.backend.assorttis.mappers;

import com.backend.assorttis.dto.organization.OrganizationCertificationDTO;
import com.backend.assorttis.dto.organization.OrganizationDTO;
import com.backend.assorttis.dto.organization.OrganizationSavedSearchDTO;
import com.backend.assorttis.dto.sector.SubsectorDTO;
import com.backend.assorttis.entities.*;
import com.backend.assorttis.entities.enums.project.ProjectStatus;
import com.backend.assorttis.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
public class OrganizationMapper {

    private final ProjectOrganizationRepository projectOrganizationRepository;
    private final ProjectRepository projectRepository;
    private final OrganizationSectorRepository organizationSectorRepository;
    private final OrganizationSubsectorRepository organizationSubsectorRepository;
    private final OrganizationLanguageRepository organizationLanguageRepository;
    private final OrganizationServiceRepository organizationServiceRepository;
    private final PartnershipRepository partnershipRepository;
    private final OrganizationCertificationRepository organizationCertificationRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final OrganizationSubscriptionSectorRepository organizationSubscriptionSectorRepository;

    @Autowired
    public OrganizationMapper(ProjectOrganizationRepository projectOrganizationRepository,
                              ProjectRepository projectRepository,
                              OrganizationSectorRepository organizationSectorRepository,
                              OrganizationSubsectorRepository organizationSubsectorRepository,
                              OrganizationLanguageRepository organizationLanguageRepository,
                              OrganizationServiceRepository organizationServiceRepository,
                              PartnershipRepository partnershipRepository,
                              OrganizationCertificationRepository organizationCertificationRepository,
                              TeamMemberRepository teamMemberRepository,
                              OrganizationSubscriptionSectorRepository organizationSubscriptionSectorRepository) {
        this.projectOrganizationRepository = projectOrganizationRepository;
        this.projectRepository = projectRepository;
        this.organizationSectorRepository = organizationSectorRepository;
        this.organizationSubsectorRepository = organizationSubsectorRepository;
        this.organizationLanguageRepository = organizationLanguageRepository;
        this.organizationServiceRepository = organizationServiceRepository;
        this.partnershipRepository = partnershipRepository;
        this.organizationCertificationRepository = organizationCertificationRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.organizationSubscriptionSectorRepository = organizationSubscriptionSectorRepository;
    }

    public OrganizationDTO toDTO(Organization organization) {
        if (organization == null) {
            return null;
        }

        OrganizationDTO dto = new OrganizationDTO();
        dto.setId(organization.getId());
        dto.setName(organization.getName());
        dto.setCleanName(organization.getCleanName());
        dto.setLegalName(organization.getLegalName());
        dto.setType(organization.getType());
        dto.setRegistrationNumber(organization.getRegistrationNumber());
        dto.setYearFounded(organization.getYearFounded());
        dto.setEmployeesCount(organization.getEmployeesCount());
        dto.setAnnualTurnover(organization.getAnnualTurnover());
        dto.setWebsite(organization.getWebsite());
        dto.setLogoUrl(organization.getLogoUrl());
        dto.setDescription(organization.getDescription());
        dto.setValidated(organization.getValidated());
        dto.setVerifiedAt(organization.getVerifiedAt());
        dto.setRatingAvg(organization.getRatingAvg());
        dto.setLatitude(organization.getLatitude());
        dto.setLongitude(organization.getLongitude());
        dto.setIsPartner(organization.getIsPartner());
        dto.setSlogan(organization.getSlogan());
        dto.setContactEmail(organization.getContactEmail());
        dto.setContactPhone(organization.getContactPhone());
        dto.setAddress(organization.getAddress());
        
        if (organization.getVerificationStatus() != null) {
            dto.setVerificationStatus(organization.getVerificationStatus().name());
        }
        
        dto.setProfileValidationStatus(organization.getProfileValidationStatus());
        dto.setAcronym(organization.getAcronym());
        dto.setRegion(organization.getRegion());
        dto.setIsActive(organization.getIsActive());
        dto.setPostalCode(organization.getPostalCode());
        dto.setEquipmentInfrastructure(organization.getEquipmentInfrastructure());
        dto.setContactName(organization.getContactName());
        dto.setContactTitle(organization.getContactTitle());
        dto.setOperatingRegions(organization.getOperatingRegionsRaw() == null || organization.getOperatingRegionsRaw().isBlank()
                ? (organization.getRegion() == null || organization.getRegion().isBlank()
                    ? List.of()
                    : List.of(organization.getRegion()))
                : Arrays.stream(organization.getOperatingRegionsRaw().split(","))
                    .map(String::trim)
                    .filter(value -> !value.isBlank())
                    .toList());

        // Country Mapping
        OrganizationDTO.CountryDTO countryDTO = new OrganizationDTO.CountryDTO();
        if (organization.getCountry() != null) {
            countryDTO.setId(organization.getCountry().getId());
            countryDTO.setName(organization.getCountry().getName());
            countryDTO.setCode(organization.getCountry().getCode());
        }
        if (organization.getCountryNameOverride() != null && !organization.getCountryNameOverride().isBlank()) {
            countryDTO.setName(organization.getCountryNameOverride());
        }
        dto.setCountry(countryDTO);

        // City Mapping
        OrganizationDTO.CityDTO cityDTO = new OrganizationDTO.CityDTO();
        if (organization.getCity() != null) {
            cityDTO.setId(organization.getCity().getId());
            cityDTO.setName(organization.getCity().getName());
        } else if (organization.getCityNameOverride() == null || organization.getCityNameOverride().isBlank()) {
            cityDTO.setName("N/A");
        }
        if (organization.getCityNameOverride() != null && !organization.getCityNameOverride().isBlank()) {
            cityDTO.setName(organization.getCityNameOverride());
        }
        dto.setCity(cityDTO);

        // Parent Mapping
        if (organization.getParent() != null) {
            dto.setParentId(organization.getParent().getId());
        }

        // Main Sector Mapping
        if (organization.getMainSector() != null) {
            OrganizationDTO.SectorDTO sectorDTO = new OrganizationDTO.SectorDTO();
            sectorDTO.setId(organization.getMainSector().getId());
            sectorDTO.setName(organization.getMainSector().getName());
            sectorDTO.setCode(organization.getMainSector().getCode());
            dto.setMainSector(sectorDTO);
        }

        // Profile Validation Mapping
        if (organization.getProfileValidatedBy() != null) {
            dto.setProfileValidatedBy(organization.getProfileValidatedBy().getFirstName() + " " + organization.getProfileValidatedBy().getLastName());
        }
        dto.setProfileValidatedAt(organization.getProfileValidatedAt());

        // Other metadata
        if (organization.getDefaultPaymentMethod() != null) {
            dto.setDefaultPaymentMethod(organization.getDefaultPaymentMethod().getMethodType());
        }
        dto.setCreatedAt(organization.getCreatedAt());
        dto.setUpdatedAt(organization.getUpdatedAt());

        try {
            List<ProjectOrganization> pos = projectOrganizationRepository.findAll();
            List<ProjectOrganization> orgProjectOrgs = pos.stream()
                .filter(po -> po.getOrganization() != null && po.getOrganization().getId().equals(organization.getId()))
                .toList();

            long activeCount = orgProjectOrgs.stream()
                .map(po -> po.getProject())
                .filter(p -> p != null && ProjectStatus.ACTIVE.equals(p.getStatus()))
                .count();
            dto.setActiveProjects(activeCount);

            long completedCount = orgProjectOrgs.stream()
                .map(po -> po.getProject())
                .filter(p -> p != null && ProjectStatus.COMPLETED.equals(p.getStatus()))
                .count();
            dto.setCompletedProjects(organization.getProfileProjectsCompleted() != null
                    ? organization.getProfileProjectsCompleted()
                    : completedCount);

            BigDecimal totalBudget = orgProjectOrgs.stream()
                .map(po -> po.getProject())
                .filter(p -> p != null && p.getBudget() != null)
                .map(p -> p.getBudget())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            dto.setBudget(totalBudget);
        } catch (Exception e) {
            dto.setActiveProjects(0L);
            dto.setCompletedProjects(organization.getProfileProjectsCompleted() != null
                    ? organization.getProfileProjectsCompleted()
                    : 0L);
            dto.setBudget(BigDecimal.ZERO);
        }

        try {
            List<Partnership> partnerships = partnershipRepository.findAll();
            long count = partnerships.stream()
                .filter(p -> (p.getOrganization() != null && p.getOrganization().getId().equals(organization.getId())) ||
                             (p.getPartnerOrganization() != null && p.getPartnerOrganization().getId().equals(organization.getId())))
                .count();
            dto.setPartnerships(count);
            dto.setPartnershipNames(partnerships.stream()
                .filter(p -> (p.getOrganization() != null && p.getOrganization().getId().equals(organization.getId())) ||
                             (p.getPartnerOrganization() != null && p.getPartnerOrganization().getId().equals(organization.getId())))
                .map(p -> {
                    if (p.getOrganization() != null && p.getOrganization().getId().equals(organization.getId())) {
                        return p.getPartnerOrganization() != null ? p.getPartnerOrganization().getName() : null;
                    }
                    return p.getOrganization() != null ? p.getOrganization().getName() : null;
                })
                .filter(name -> name != null && !name.isBlank())
                .distinct()
                .toList());
        } catch (Exception e) {
            dto.setPartnerships(0L);
        }

        try {
            List<OrganizationCertification> certs = organizationCertificationRepository.findByOrganizationId(organization.getId());
            List<OrganizationCertificationDTO> certDtos = certs.stream()
                .map(c -> {
                    OrganizationCertificationDTO cDto = new OrganizationCertificationDTO();
                    cDto.setId(c.getId());
                    cDto.setCertificationName(c.getCertificationName());
                    cDto.setIssuingOrganization(c.getIssuingOrganization());
                    cDto.setIssuedDate(c.getIssuedDate());
                    cDto.setExpiryDate(c.getExpiryDate());
                    cDto.setCredentialId(c.getCredentialId());
                    cDto.setCredentialUrl(c.getCredentialUrl());
                    cDto.setCreatedAt(c.getCreatedAt());
                    return cDto;
                })
                .toList();
            dto.setCertifications(certDtos);
        } catch (Exception e) {
            System.err.println("Error mapping certifications: " + e.getMessage());
            dto.setCertifications(new java.util.ArrayList<>());
        }

        try {
            List<TeamMember> members = teamMemberRepository.findAll();
            long memberCount = members.stream()
                .filter(tm -> tm.getTeam() != null && tm.getTeam().getOrganization() != null &&
                              tm.getTeam().getOrganization().getId().equals(organization.getId()))
                .count();
            dto.setTeamMembers(memberCount);
        } catch (Exception e) {
            dto.setTeamMembers(0L);
        }

        try {
            List<OrganizationSubscriptionSector> subSectors = organizationSubscriptionSectorRepository.findById_OrganizationId(organization.getId());
            List<OrganizationDTO.SectorDTO> mappedSectors = subSectors.stream()
                .map(os -> os.getSector())
                .filter(s -> s != null)
                .map(s -> {
                    OrganizationDTO.SectorDTO sDto = new OrganizationDTO.SectorDTO();
                    sDto.setId(s.getId());
                    sDto.setName(s.getName());
                    sDto.setCode(s.getCode());
                    return sDto;
                })
                .toList();
            dto.setSectors(mappedSectors);
        } catch (Exception e) {
            // ignore
        }

        try {
            List<OrganizationSubsector> orgSubsectors = organizationSubsectorRepository.findAll();
            List<SubsectorDTO> mappedSubsectors = orgSubsectors.stream()
                .filter(os -> os.getOrganization() != null && os.getOrganization().getId().equals(organization.getId()))
                .map(os -> os.getSubsector())
                .filter(ss -> ss != null)
                .map(ss -> {
                    SubsectorDTO ssDto = new SubsectorDTO();
                    ssDto.setId(ss.getId());
                    ssDto.setName(ss.getName());
                    ssDto.setCode(ss.getCode());
                    if (ss.getSector() != null) {
                        ssDto.setSectorId(ss.getSector().getId());
                    }
                    return ssDto;
                })
                .toList();
            dto.setSubsectors(mappedSubsectors);
        } catch (Exception e) {
            // ignore
        }

        try {
            dto.setLanguages(organizationLanguageRepository.findByOrganizationId(organization.getId()).stream()
                    .map(OrganizationLanguage::getLanguageCode)
                    .filter(language -> language != null && language.getName() != null && !language.getName().isBlank())
                    .map(Language::getName)
                    .distinct()
                    .toList());
        } catch (Exception e) {
            // ignore
        }

        try {
            dto.setServices(organizationServiceRepository.findByOrganizationId(organization.getId()).stream()
                    .map(OrganizationService::getService)
                    .filter(service -> service != null && service.getLabel() != null && !service.getLabel().isBlank())
                    .map(Service::getLabel)
                    .distinct()
                    .toList());
        } catch (Exception e) {
            // ignore
        }

        return dto;
    }

    public OrganizationSavedSearchDTO toSavedSearchDTO(OrganizationSavedSearch savedSearch) {
        if (savedSearch == null) return null;
        OrganizationSavedSearchDTO dto = new OrganizationSavedSearchDTO();
        dto.setId(savedSearch.getId());
        dto.setLabel(savedSearch.getName());
        dto.setPayload(savedSearch.getPayload());
        dto.setCreatedAt(savedSearch.getCreatedAt());
        return dto;
    }
}
