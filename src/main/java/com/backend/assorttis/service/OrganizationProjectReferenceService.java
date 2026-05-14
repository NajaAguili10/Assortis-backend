package com.backend.assorttis.service;

import com.backend.assorttis.dto.organization.OrganizationProjectReferenceDTO;
import com.backend.assorttis.dto.organization.OrganizationProjectReferenceDocumentDTO;
import com.backend.assorttis.dto.organization.OrganizationProjectReferenceRequest;
import com.backend.assorttis.entities.*;
import com.backend.assorttis.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class OrganizationProjectReferenceService {
    private static final Map<String, String> REGION_ALIASES = createRegionAliases();
    private static final Map<String, String> DONOR_NAME_ALIASES = createDonorNameAliases();

    private final OrganizationProjectReferenceRepository referenceRepository;
    private final OrganizationUserRepository organizationUserRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final CountryRepository countryRepository;
    private final RegionRepository regionRepository;
    private final SectorRepository sectorRepository;
    private final SubsectorRepository subsectorRepository;
    private final DonorRepository donorRepository;

    @Transactional(readOnly = true)
    public List<OrganizationProjectReferenceDTO> getCurrentOrganizationReferences(Long userId) {
        Organization organization = resolveCurrentOrganization(userId);
        return referenceRepository.findByOrganizationIdOrderByUpdatedAtDesc(organization.getId()).stream()
                .sorted(Comparator.comparing(this::resolveLastUpdated).reversed())
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public OrganizationProjectReferenceDTO getCurrentOrganizationReference(Long userId, Long referenceId) {
        Organization organization = resolveCurrentOrganization(userId);
        return toDTO(findScopedReference(referenceId, organization.getId()));
    }

    @Transactional
    public OrganizationProjectReferenceDTO createCurrentOrganizationReference(
            Long userId,
            OrganizationProjectReferenceRequest request
    ) {
        Organization organization = resolveCurrentOrganization(userId);
        Instant now = Instant.now();

        OrganizationProjectReference reference = new OrganizationProjectReference();
        reference.setOrganization(organization);
        applyRequest(reference, request);
        if (!StringUtils.hasText(reference.getReferenceNumber())) {
            reference.setReferenceNumber(buildReferenceNumber(organization.getId()));
        }
        reference.setCreatedAt(now);
        reference.setUpdatedAt(now);

        return toDTO(referenceRepository.save(reference));
    }

    @Transactional
    public OrganizationProjectReferenceDTO updateCurrentOrganizationReference(
            Long userId,
            Long referenceId,
            OrganizationProjectReferenceRequest request
    ) {
        Organization organization = resolveCurrentOrganization(userId);
        OrganizationProjectReference reference = findScopedReference(referenceId, organization.getId());

        String existingReferenceNumber = reference.getReferenceNumber();
        applyRequest(reference, request);
        if (!StringUtils.hasText(reference.getReferenceNumber())) {
            reference.setReferenceNumber(existingReferenceNumber);
        }
        reference.setUpdatedAt(Instant.now());

        return toDTO(referenceRepository.save(reference));
    }

    @Transactional
    public void deleteCurrentOrganizationReference(Long userId, Long referenceId) {
        Organization organization = resolveCurrentOrganization(userId);
        OrganizationProjectReference reference = findScopedReference(referenceId, organization.getId());
        referenceRepository.delete(reference);
    }

    private OrganizationProjectReference findScopedReference(Long referenceId, Long organizationId) {
        return referenceRepository.findByIdAndOrganizationId(referenceId, organizationId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Project reference not found"));
    }

    private Organization resolveCurrentOrganization(Long userId) {
        return organizationUserRepository.findMembershipsByUserId(userId).stream()
                .filter(membership -> !"inactive".equalsIgnoreCase(nullToEmpty(membership.getMembershipStatus())))
                .map(OrganizationUser::getOrganization)
                .findFirst()
                .orElseGet(() -> teamMemberRepository.findMembershipsByUserId(userId).stream()
                        .map(teamMember -> teamMember.getTeam().getOrganization())
                        .findFirst()
                        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "No organization found for user")));
    }

    private void applyRequest(OrganizationProjectReference reference, OrganizationProjectReferenceRequest request) {
        reference.setReferenceNumber(trimToNull(request.getReferenceNumber()));
        reference.setTitle(defaultIfBlank(request.getTitle(), "Untitled reference"));
        reference.setSummary(trimToEmpty(request.getSummary()));
        reference.setDescription(trimToEmpty(request.getDescription()));
        Country country = resolveCountry(request.getCountry());
        Region region = resolveRegion(request.getRegion());
        if (country != null && region != null && !StringUtils.hasText(country.getRegionWorld())) {
            country.setRegionWorld(normalizeRegionCode(region));
            country = countryRepository.save(country);
        }
        reference.setCountry(country);
        reference.setRegion(region);
        Sector sector = resolveSector(request.getSector());
        reference.setSector(sector);
        reference.setSubsector(resolveSubsector(request.getSubSector(), sector));
        reference.setClient(trimToEmpty(request.getClient()));
        reference.setDonor(resolveDonor(request.getDonor()));
        reference.setStartDate(request.getStartDate());
        reference.setEndDate(request.getEndDate());
        reference.setStatus(normalizeStatus(request.getStatus()));
        reference.setReferenceType(trimToNull(request.getReferenceType()));
        reference.setUrl(trimToNull(request.getUrl()));
        replaceDocuments(reference, request.getDocuments());
    }

    private void replaceDocuments(
            OrganizationProjectReference reference,
            List<OrganizationProjectReferenceDocumentDTO> requestedDocuments
    ) {
        List<OrganizationProjectReferenceDocument> nextDocuments = new ArrayList<>();
        List<OrganizationProjectReferenceDocument> existingDocuments = reference.getDocuments() == null
                ? List.of()
                : reference.getDocuments();

        for (OrganizationProjectReferenceDocumentDTO documentDTO : requestedDocuments == null ? List.<OrganizationProjectReferenceDocumentDTO>of() : requestedDocuments) {
            OrganizationProjectReferenceDocument document = existingDocuments.stream()
                    .filter(existing -> existing.getId() != null && existing.getId().equals(documentDTO.getId()))
                    .findFirst()
                    .orElseGet(OrganizationProjectReferenceDocument::new);

            document.setProjectReference(reference);
            document.setName(trimToEmpty(documentDTO.getName()));
            document.setType(defaultIfBlank(documentDTO.getType(), "report"));
            document.setUploadedAt(documentDTO.getUploadedAt() != null ? documentDTO.getUploadedAt() : Instant.now());
            document.setMimeType(trimToNull(documentDTO.getMimeType()));
            document.setSize(documentDTO.getSize());
            document.setContentDataUrl(trimToNull(documentDTO.getContentDataUrl()));
            nextDocuments.add(document);
        }

        reference.getDocuments().clear();
        reference.getDocuments().addAll(nextDocuments);
    }

    private Country resolveCountry(String code) {
        if (!StringUtils.hasText(code)) {
            return null;
        }
        String normalizedCode = code.trim().toUpperCase(Locale.ROOT);
        return countryRepository.findByCode(normalizedCode)
                .or(() -> countryRepository.findByNameIgnoreCase(code.trim()))
                .orElseGet(() -> {
                    Country country = new Country();
                    country.setId(nextCountryId());
                    country.setCode(normalizedCode);
                    country.setName(humanizeCountryCode(normalizedCode));
                    country.setRegionWorld(inferRegionWorldFromCountryCode(normalizedCode));
                    country.setIsActive(true);
                    return countryRepository.save(country);
                });
    }

    private Region resolveRegion(String value) {
        if (!StringUtils.hasText(value)) {
            return null;
        }
        String normalized = value.trim();
        String alias = REGION_ALIASES.getOrDefault(normalized.toUpperCase(Locale.ROOT), normalized);
        return regionRepository.findByCodeIgnoreCase(alias)
                .or(() -> regionRepository.findByNameIgnoreCase(alias))
                .or(() -> regionRepository.findByCodeIgnoreCase(normalized))
                .or(() -> regionRepository.findByNameIgnoreCase(humanizeCode(normalized)))
                .orElseGet(() -> {
                    Region region = new Region();
                    region.setId(nextRegionId());
                    region.setCode(normalized.toUpperCase(Locale.ROOT));
                    region.setName(humanizeCode(normalized));
                    return regionRepository.save(region);
                });
    }

    private Sector resolveSector(String code) {
        if (!StringUtils.hasText(code)) {
            return null;
        }
        String normalized = code.trim().toUpperCase(Locale.ROOT);
        return sectorRepository.findByCode(normalized)
                .or(() -> sectorRepository.findByName(humanizeCode(normalized)))
                .orElseGet(() -> {
                    Sector sector = new Sector();
                    sector.setId(nextSectorId());
                    sector.setCode(normalized);
                    sector.setName(humanizeCode(normalized));
                    return sectorRepository.save(sector);
                });
    }

    private Subsector resolveSubsector(String code, Sector sector) {
        if (!StringUtils.hasText(code)) {
            return null;
        }
        String normalized = code.trim().toUpperCase(Locale.ROOT);
        return subsectorRepository.findByCode(normalized)
                .orElseGet(() -> {
                    Subsector subsector = new Subsector();
                    subsector.setId(nextSubsectorId());
                    subsector.setSector(sector);
                    subsector.setCode(normalized);
                    subsector.setName(humanizeCode(normalized));
                    return subsectorRepository.save(subsector);
                });
    }

    private Donor resolveDonor(String value) {
        if (!StringUtils.hasText(value)) {
            return null;
        }
        String normalized = value.trim().toUpperCase(Locale.ROOT);
        return donorRepository.findByShortNameIgnoreCase(normalized)
                .or(() -> donorRepository.findByNameIgnoreCase(value.trim()))
                .or(() -> donorRepository.findByNameIgnoreCase(DONOR_NAME_ALIASES.getOrDefault(normalized, humanizeCode(normalized))))
                .orElseGet(() -> {
                    Donor donor = new Donor();
                    donor.setId(nextDonorId());
                    donor.setShortName(normalized);
                    donor.setName(DONOR_NAME_ALIASES.getOrDefault(normalized, humanizeCode(normalized)));
                    donor.setType("EXTERNAL");
                    donor.setCreatedAt(Instant.now());
                    return donorRepository.save(donor);
                });
    }

    private String normalizeStatus(String value) {
        String normalized = nullToEmpty(value).trim();
        if ("verified".equalsIgnoreCase(normalized)) {
            return "verified";
        }
        return "notVerified";
    }

    private String buildReferenceNumber(Long organizationId) {
        long nextSequence = referenceRepository.countByOrganizationId(organizationId) + 1;
        return "ORG-REF-" + java.time.Year.now() + "-" + String.format("%03d", nextSequence);
    }

    private OrganizationProjectReferenceDTO toDTO(OrganizationProjectReference reference) {
        OrganizationProjectReferenceDTO dto = new OrganizationProjectReferenceDTO();
        dto.setId(reference.getId());
        dto.setOrganizationId(reference.getOrganization().getId());
        dto.setReferenceNumber(reference.getReferenceNumber());
        dto.setTitle(reference.getTitle());
        dto.setSummary(reference.getSummary());
        dto.setDescription(reference.getDescription());
        dto.setCountry(reference.getCountry() != null ? reference.getCountry().getCode() : null);
        dto.setCountryName(reference.getCountry() != null ? reference.getCountry().getName() : null);
        dto.setRegion(resolveRegionCode(reference));
        dto.setRegionLabel(resolveRegionLabel(reference));
        dto.setSector(reference.getSector() != null ? reference.getSector().getCode() : null);
        dto.setSectorName(reference.getSector() != null ? reference.getSector().getName() : null);
        dto.setSubSector(reference.getSubsector() != null ? reference.getSubsector().getCode() : null);
        dto.setSubSectorName(reference.getSubsector() != null ? reference.getSubsector().getName() : null);
        dto.setClient(reference.getClient());
        dto.setDonor(resolveDonorCode(reference.getDonor()));
        dto.setDonorName(reference.getDonor() != null ? reference.getDonor().getName() : null);
        dto.setStartDate(reference.getStartDate());
        dto.setEndDate(reference.getEndDate());
        dto.setStatus(normalizeStatus(reference.getStatus()));
        dto.setReferenceType(reference.getReferenceType());
        dto.setUrl(reference.getUrl());
        dto.setDocuments(reference.getDocuments().stream().map(this::toDocumentDTO).toList());
        dto.setCreatedAt(reference.getCreatedAt());
        dto.setUpdatedAt(reference.getUpdatedAt());
        return dto;
    }

    private OrganizationProjectReferenceDocumentDTO toDocumentDTO(OrganizationProjectReferenceDocument document) {
        OrganizationProjectReferenceDocumentDTO dto = new OrganizationProjectReferenceDocumentDTO();
        dto.setId(document.getId());
        dto.setName(document.getName());
        dto.setType(document.getType());
        dto.setUploadedAt(document.getUploadedAt());
        dto.setMimeType(document.getMimeType());
        dto.setSize(document.getSize());
        dto.setContentDataUrl(document.getContentDataUrl());
        return dto;
    }

    private String resolveRegionCode(OrganizationProjectReference reference) {
        if (reference.getCountry() != null && StringUtils.hasText(reference.getCountry().getRegionWorld())) {
            return reference.getCountry().getRegionWorld();
        }
        if (reference.getRegion() != null && StringUtils.hasText(reference.getRegion().getCode())) {
            return normalizeRegionCode(reference.getRegion());
        }
        return null;
    }

    private String resolveRegionLabel(OrganizationProjectReference reference) {
        if (reference.getRegion() != null && StringUtils.hasText(reference.getRegion().getName())) {
            return reference.getRegion().getName();
        }
        return reference.getCountry() != null ? humanizeCode(reference.getCountry().getRegionWorld()) : null;
    }

    private String resolveDonorCode(Donor donor) {
        if (donor == null) {
            return null;
        }
        if (StringUtils.hasText(donor.getShortName())) {
            return donor.getShortName().trim();
        }
        if (StringUtils.hasText(donor.getName())) {
            return donor.getName().trim();
        }
        return null;
    }

    private Instant resolveLastUpdated(OrganizationProjectReference reference) {
        return Optional.ofNullable(reference.getUpdatedAt())
                .orElse(Optional.ofNullable(reference.getCreatedAt()).orElse(Instant.EPOCH));
    }

    private String trimToNull(String value) {
        return StringUtils.hasText(value) ? value.trim() : null;
    }

    private String trimToEmpty(String value) {
        return trimToNull(value) == null ? "" : value.trim();
    }

    private String defaultIfBlank(String value, String fallback) {
        return trimToNull(value) == null ? fallback : value.trim();
    }

    private String nullToEmpty(String value) {
        return value == null ? "" : value;
    }

    private Long nextCountryId() {
        return countryRepository.findAll().stream().map(Country::getId).filter(java.util.Objects::nonNull).max(Long::compareTo).orElse(0L) + 1;
    }

    private Long nextRegionId() {
        return regionRepository.findAll().stream().map(Region::getId).filter(java.util.Objects::nonNull).max(Long::compareTo).orElse(0L) + 1;
    }

    private Long nextSectorId() {
        return sectorRepository.findAll().stream().map(Sector::getId).filter(java.util.Objects::nonNull).max(Long::compareTo).orElse(0L) + 1;
    }

    private Long nextSubsectorId() {
        return subsectorRepository.findAll().stream().map(Subsector::getId).filter(java.util.Objects::nonNull).max(Long::compareTo).orElse(0L) + 1;
    }

    private Long nextDonorId() {
        return donorRepository.findAll().stream().map(Donor::getId).filter(java.util.Objects::nonNull).max(Long::compareTo).orElse(0L) + 1;
    }

    private String humanizeCountryCode(String code) {
        return switch (code) {
            case "CI" -> "Cote d'Ivoire";
            case "GB" -> "United Kingdom";
            case "US" -> "United States";
            case "AE" -> "United Arab Emirates";
            default -> humanizeCode(code);
        };
    }

    private String inferRegionWorldFromCountryCode(String code) {
        return switch (code) {
            case "PL", "CZ", "HU", "SK", "SI" -> "CENTRAL_EASTERN_EUROPE";
            case "AL", "BA", "BG", "HR", "ME", "RS" -> "SOUTHEASTERN_EUROPE";
            case "FR", "DE", "GB", "ES", "IT", "BE", "NL", "CH", "AT", "PT" -> "WESTERN_EUROPE";
            case "CM", "TD", "CF", "CG", "CD", "GA" -> "CENTRAL_AFRICA";
            case "KE", "TZ", "UG", "ET", "RW", "BI", "SO" -> "EAST_AFRICA";
            case "EG", "MA", "TN", "DZ", "LY" -> "NORTH_AFRICA";
            case "ZA", "ZW", "ZM", "MZ", "BW", "NA" -> "SOUTHERN_AFRICA";
            case "NG", "GH", "SN", "CI", "ML", "NE", "BF", "BJ", "TG" -> "WEST_AFRICA";
            case "KZ", "UZ", "TM", "KG", "TJ" -> "CENTRAL_ASIA";
            case "SA", "AE", "QA", "KW", "BH", "OM", "JO", "LB", "IQ", "YE" -> "MIDDLE_EAST";
            case "CN", "JP", "KR", "KP", "MN" -> "NORTHEAST_ASIA";
            case "TH", "VN", "PH", "ID", "MY", "SG", "MM", "KH" -> "SOUTHEAST_ASIA";
            case "IN", "PK", "BD", "LK", "NP", "AF" -> "SOUTH_ASIA";
            case "AU", "NZ", "FJ", "PG" -> "OCEANIA";
            case "MX", "GT", "HN", "SV", "NI", "CR", "PA" -> "CENTRAL_AMERICA";
            case "US", "CA" -> "NORTH_AMERICA";
            default -> null;
        };
    }

    private String normalizeRegionCode(Region region) {
        String code = region.getCode();
        if (!StringUtils.hasText(code)) {
            return humanizeCode(region.getName()).toUpperCase(Locale.ROOT).replace(' ', '_');
        }
        return switch (code.trim().toUpperCase(Locale.ROOT)) {
            case "AFRICA_WEST", "WEST_AFRICA" -> "WEST_AFRICA";
            case "AFRICA_EAST", "EAST_AFRICA" -> "EAST_AFRICA";
            case "AFRICA_CENTRAL", "CENTRAL_AFRICA" -> "CENTRAL_AFRICA";
            case "AFRICA_NORTH", "NORTH_AFRICA" -> "NORTH_AFRICA";
            case "AFRICA_SOUTHERN", "SOUTHERN_AFRICA" -> "SOUTHERN_AFRICA";
            case "EUROPE", "WESTERN_EUROPE" -> "WESTERN_EUROPE";
            case "MIDDLE_EAST" -> "MIDDLE_EAST";
            case "NORTH_AMERICA" -> "NORTH_AMERICA";
            case "SOUTH_AMERICA" -> "SOUTH_AMERICA";
            case "OCEANIA" -> "OCEANIA";
            default -> code.trim().toUpperCase(Locale.ROOT);
        };
    }

    private String humanizeCode(String value) {
        if (!StringUtils.hasText(value)) {
            return "";
        }
        String[] words = value.trim().replace('_', ' ').toLowerCase(Locale.ROOT).split("\\s+");
        StringBuilder builder = new StringBuilder();
        for (String word : words) {
            if (word.isEmpty()) {
                continue;
            }
            if (builder.length() > 0) {
                builder.append(' ');
            }
            builder.append(Character.toUpperCase(word.charAt(0)));
            if (word.length() > 1) {
                builder.append(word.substring(1));
            }
        }
        return builder.toString();
    }

    private static Map<String, String> createRegionAliases() {
        Map<String, String> aliases = new HashMap<>();
        aliases.put("WEST_AFRICA", "West Africa");
        aliases.put("EAST_AFRICA", "East Africa");
        aliases.put("CENTRAL_AFRICA", "Central Africa");
        aliases.put("NORTH_AFRICA", "North Africa");
        aliases.put("SOUTHERN_AFRICA", "Southern Africa");
        aliases.put("WESTERN_EUROPE", "Europe");
        aliases.put("CENTRAL_EASTERN_EUROPE", "Europe");
        aliases.put("SOUTHEASTERN_EUROPE", "Europe");
        aliases.put("CENTRAL_ASIA", "Asia Pacific");
        aliases.put("SOUTHEAST_ASIA", "Asia Pacific");
        aliases.put("SOUTH_ASIA", "Asia Pacific");
        aliases.put("NORTHEAST_ASIA", "Asia Pacific");
        aliases.put("NORTH_AMERICA", "North America");
        aliases.put("CENTRAL_AMERICA", "Caribbean");
        aliases.put("OCEANIA", "Oceania");
        aliases.put("MIDDLE_EAST", "Middle East");
        return aliases;
    }

    private static Map<String, String> createDonorNameAliases() {
        Map<String, String> aliases = new HashMap<>();
        aliases.put("WB", "World Bank");
        aliases.put("AFDB", "African Development Bank");
        aliases.put("EC", "European Commission");
        aliases.put("EU_INSTITUTIONS", "European Union Institutions");
        aliases.put("UNW", "UN Women");
        aliases.put("UNWFP", "World Food Programme");
        aliases.put("USAID", "USAID");
        aliases.put("UNICEF", "UNICEF");
        aliases.put("FAO", "Food and Agriculture Organization");
        aliases.put("AFD", "Agence Francaise de Developpement");
        return aliases;
    }
}
