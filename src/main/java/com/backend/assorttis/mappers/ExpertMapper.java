package com.backend.assorttis.mappers;

import com.backend.assorttis.dto.expert.*;
import com.backend.assorttis.dto.organization.OrganizationDTO;
import com.backend.assorttis.entities.*;
import com.backend.assorttis.entities.enums.VerificationStatus;
import com.backend.assorttis.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ExpertMapper {

    private final ExpertSubscriptionSectorRepository expertSubscriptionSectorRepository;
    private final ExpertSkillRepository expertSkillRepository;
    private final ExpertLanguageRepository expertLanguageRepository;
    private final ExpertExperienceRepository expertExperienceRepository;
    private final ExpertEducationRepository expertEducationRepository;
    private final ExpertCertificationRepository expertCertificationRepository;

    public ExpertDTO toDTO(Expert expert) {
        if (expert == null) {
            return null;
        }

        ExpertDTO dto = new ExpertDTO();
        dto.setId(expert.getId());
        dto.setFullName(expert.getFullName());
        
        if (expert.getUser() != null) {
            dto.setFirstName(expert.getUser().getFirstName());
            dto.setLastName(expert.getUser().getLastName());
            dto.setEmail(expert.getUser().getEmail());
            dto.setPhone(expert.getUser().getPhone());
        }
        
        dto.setCurrentPosition(expert.getCurrentPosition());
        dto.setYearsExperience(expert.getYearsExperience());
        dto.setDailyRate(expert.getDailyRate());
        dto.setCurrency(expert.getCurrency());
        dto.setAvailabilityStatus(expert.getAvailabilityStatus());
        dto.setAvailabilityPercentage(expert.getAvailabilityPercentage());
        dto.setProfileSummary(expert.getProfileSummary());
        dto.setRatingAvg(expert.getRatingAvg());
        
        if (expert.getVerificationStatus() != null) {
            dto.setVerificationStatus(expert.getVerificationStatus().name());
        }
        
        dto.setLevel(expert.getLevel());
        dto.setIsBidWriter(expert.getIsBidWriter());
        dto.setLastActiveAt(expert.getLastActiveAt());
        
        // Country & City
        if (expert.getCountry() != null) {
            OrganizationDTO.CountryDTO countryDTO = new OrganizationDTO.CountryDTO();
            countryDTO.setId(expert.getCountry().getId());
            countryDTO.setName(expert.getCountry().getName());
            countryDTO.setCode(expert.getCountry().getCode());
            dto.setCountry(countryDTO);
        }
        if (expert.getCity() != null) {
            OrganizationDTO.CityDTO cityDTO = new OrganizationDTO.CityDTO();
            cityDTO.setId(expert.getCity().getId());
            cityDTO.setName(expert.getCity().getName());
            dto.setCity(cityDTO);
        }
        
        // Organization
        if (expert.getPrimaryOrganization() != null) {
            dto.setPrimaryOrganizationName(expert.getPrimaryOrganization().getName());
        }
        
        // Verified flag
        dto.setVerified(VerificationStatus.VERIFIED.equals(expert.getVerificationStatus()));

        // Sectors
        try {
            List<ExpertSubscriptionSector> expertSectors = expertSubscriptionSectorRepository.findByExpert(expert);
            dto.setSectors(expertSectors.stream()
                    .map(es -> {
                        ExpertSectorDTO sectorDTO = new ExpertSectorDTO();
                        if (es.getSector() != null) {
                            sectorDTO.setSectorName(es.getSector().getName());
                            sectorDTO.setSectorCode(es.getSector().getCode());
                        }
                        return sectorDTO;
                    })
                    .collect(Collectors.toList()));
        } catch (Exception e) {}

        // Skills
        try {
            List<ExpertSkill> expertSkills = expertSkillRepository.findAll();
            dto.setSkills(expertSkills.stream()
                    .filter(es -> es.getExpert() != null && es.getExpert().getId().equals(expert.getId()))
                    .map(es -> {
                        ExpertSkillDTO skillDTO = new ExpertSkillDTO();
                        if (es.getSkill() != null) {
                            skillDTO.setSkillName(es.getSkill().getName());
                        }
                        skillDTO.setLevel(es.getLevel());
                        return skillDTO;
                    })
                    .collect(Collectors.toList()));
        } catch (Exception e) {}

        // Languages
        try {
            List<ExpertLanguage> expertLanguages = expertLanguageRepository.findAll();
            dto.setLanguages(expertLanguages.stream()
                    .filter(el -> el.getExpert() != null && el.getExpert().getId().equals(expert.getId()))
                    .map(el -> {
                        ExpertLanguageDTO langDTO = new ExpertLanguageDTO();
                        if (el.getLanguageCode() != null) {
                            langDTO.setLanguageCode(el.getLanguageCode().getCode());
                            langDTO.setLanguageName(el.getLanguageCode().getName());
                        }
                        langDTO.setProficiency(el.getProficiency());
                        return langDTO;
                    })
                    .collect(Collectors.toList()));
        } catch (Exception e) {}

        // Education
        try {
            List<ExpertEducation> expertEducations = expertEducationRepository.findAll();
            dto.setEducations(expertEducations.stream()
                    .filter(ee -> ee.getExpert() != null && ee.getExpert().getId().equals(expert.getId()))
                    .map(ee -> {
                        ExpertEducationDTO eduDTO = new ExpertEducationDTO();
                        eduDTO.setId(ee.getId());
                        eduDTO.setDegree(ee.getDegree());
                        eduDTO.setFieldOfStudy(ee.getFieldOfStudy());
                        eduDTO.setInstitution(ee.getInstitution());
                        eduDTO.setGrade(ee.getGrade());
                        eduDTO.setGraduationYear(ee.getGraduationYear());
                        if (ee.getCountry() != null) eduDTO.setCountry(ee.getCountry().getName());
                        if (ee.getCity() != null) eduDTO.setCity(ee.getCity().getName());
                        return eduDTO;
                    })
                    .collect(Collectors.toList()));
        } catch (Exception e) {}

        // Experience
        try {
            List<ExpertExperience> expertExperiences = expertExperienceRepository.findByExpert(expert);
            dto.setExperiences(expertExperiences.stream()
                    .map(ee -> {
                        ExpertExperienceDTO expDTO = new ExpertExperienceDTO();
                        expDTO.setId(ee.getId());
                        expDTO.setTitle(ee.getTitle());
                        expDTO.setOrganization(ee.getOrganization());
                        expDTO.setDonorName(ee.getDonorName());
                        if (ee.getSector() != null) expDTO.setSectorName(ee.getSector().getName());
                        expDTO.setStartDate(ee.getStartDate());
                        expDTO.setEndDate(ee.getEndDate());
                        expDTO.setDescription(ee.getDescription());
                        if (ee.getCountry() != null) expDTO.setCountry(ee.getCountry().getName());
                        if (ee.getCity() != null) expDTO.setCity(ee.getCity().getName());
                        expDTO.setIsCurrent(ee.getIsCurrent());
                        return expDTO;
                    })
                    .collect(Collectors.toList()));
        } catch (Exception e) {}
        
        // Certifications
        try {
            List<ExpertCertification> certifications = expertCertificationRepository.findByExpert(expert);
            dto.setCertifications(certifications.stream()
                    .map(ec -> {
                        ExpertCertificationDTO certDTO = new ExpertCertificationDTO();
                        certDTO.setId(ec.getId());
                        certDTO.setName(ec.getName());
                        certDTO.setIssuingOrganization(ec.getIssuingOrganization());
                        certDTO.setIssuerName(ec.getIssuingOrganization()); // For compatibility
                        certDTO.setIssueDate(ec.getIssueDate());
                        certDTO.setExpiryDate(ec.getExpiryDate());
                        certDTO.setCredentialId(ec.getCredentialId());
                        certDTO.setCredentialUrl(ec.getCredentialUrl());
                        return certDTO;
                    })
                    .collect(Collectors.toList()));
        } catch (Exception e) {}

        return dto;
    }

    public com.backend.assorttis.dto.expert.ExpertSavedSearchDTO toSavedSearchDTO(com.backend.assorttis.entities.ExpertSavedSearch savedSearch) {
        if (savedSearch == null) return null;
        com.backend.assorttis.dto.expert.ExpertSavedSearchDTO dto = new com.backend.assorttis.dto.expert.ExpertSavedSearchDTO();
        dto.setId(savedSearch.getId());
        dto.setLabel(savedSearch.getName());
        dto.setPayload(savedSearch.getPayload());
        dto.setCreatedAt(savedSearch.getCreatedAt());
        return dto;
    }
}
