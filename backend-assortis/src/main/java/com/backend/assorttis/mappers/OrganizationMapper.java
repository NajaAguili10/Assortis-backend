package com.backend.assorttis.mappers;

import com.backend.assorttis.dto.organization.OrganizationDTO;
import com.backend.assorttis.entities.Organization;
import org.springframework.stereotype.Component;

@Component
public class OrganizationMapper {

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

        // Country Mapping
        if (organization.getCountry() != null) {
            OrganizationDTO.CountryDTO countryDTO = new OrganizationDTO.CountryDTO();
            countryDTO.setId(organization.getCountry().getId());
            countryDTO.setName(organization.getCountry().getName());
            countryDTO.setCode(organization.getCountry().getCode());
            dto.setCountry(countryDTO);
        }

        // City Mapping
        if (organization.getCity() != null) {
            OrganizationDTO.CityDTO cityDTO = new OrganizationDTO.CityDTO();
            cityDTO.setId(organization.getCity().getId());
            cityDTO.setName(organization.getCity().getName());
            dto.setCity(cityDTO);
        }

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

        return dto;
    }
}
