package com.backend.assorttis.service;

import com.backend.assorttis.dto.expert.ExpertDTO;
import com.backend.assorttis.dto.expert.ExpertSearchRequest;
import com.backend.assorttis.dto.expert.ExpertSearchResponse;
import com.backend.assorttis.entities.*;
import com.backend.assorttis.mappers.ExpertMapper;
import com.backend.assorttis.repository.ExpertRepository;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpertService {

    private final ExpertRepository expertRepository;
    private final ExpertMapper expertMapper;
    private final com.backend.assorttis.repository.InvitationRepository invitationRepository;

    @Transactional(readOnly = true)
    public List<ExpertDTO> getAllExperts() {
        return expertRepository.findAll().stream()
                .map(expertMapper::toDTO)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<ExpertDTO> getExpertsByOrganizationId(Long organizationId) {
        return expertRepository.findByPrimaryOrganizationId(organizationId).stream()
                .map(expertMapper::toDTO)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public ExpertSearchResponse searchExperts(ExpertSearchRequest filters, Pageable pageable) {
        Page<Expert> page = expertRepository.findAll(buildSpecification(filters), pageable);

        ExpertSearchResponse response = new ExpertSearchResponse();
        response.setData(page.getContent().stream().map(expertMapper::toDTO).collect(Collectors.toList()));

        ExpertSearchResponse.PaginationMeta meta = new ExpertSearchResponse.PaginationMeta();
        meta.setPage(page.getNumber());
        meta.setPageSize(page.getSize());
        meta.setTotalItems(page.getTotalElements());
        meta.setTotalPages(page.getTotalPages());
        meta.setHasNextPage(page.hasNext());
        meta.setHasPreviousPage(page.hasPrevious());
        response.setMeta(meta);
        return response;
    }

    private Specification<Expert> buildSpecification(ExpertSearchRequest filters) {
        return (root, query, cb) -> {
            query.distinct(true);
            List<Predicate> predicates = new java.util.ArrayList<>();

            Join<Expert, User> user = root.join("user", JoinType.LEFT);
            Join<Expert, Country> country = root.join("country", JoinType.LEFT);
            Join<Expert, Organization> organization = root.join("primaryOrganization", JoinType.LEFT);
            Join<Expert, Sector> mainSector = root.join("mainSector", JoinType.LEFT);

            like(predicates, cb, user.get("firstName"), filters.getFirstName());
            like(predicates, cb, user.get("lastName"), filters.getFamilyName());

            if (hasText(filters.getExpertId())) {
                try {
                    predicates.add(cb.equal(root.get("id"), Long.parseLong(filters.getExpertId().trim())));
                } catch (NumberFormatException ignored) {
                    predicates.add(cb.disjunction());
                }
            }

            if (hasText(filters.getKeywords())) {
                List<String> words = Boolean.TRUE.equals(filters.getAllWords())
                        ? List.of(filters.getKeywords().trim().split("\\s+"))
                        : List.of(filters.getKeywords().trim());
                List<Predicate> keywordPredicates = words.stream()
                        .filter(this::hasText)
                        .map(word -> keywordPredicate(root, user, country, organization, mainSector, cb, word))
                        .toList();
                if (!keywordPredicates.isEmpty()) {
                    predicates.add(Boolean.TRUE.equals(filters.getAllWords())
                            ? cb.and(keywordPredicates.toArray(Predicate[]::new))
                            : cb.or(keywordPredicates.toArray(Predicate[]::new)));
                }
            }

            Predicate sectorPredicate = sectorPredicate(query, cb, root, mainSector, filters.getSectors(), filters.getSubSectors());
            if (sectorPredicate != null) predicates.add(sectorPredicate);

            if (hasValues(filters.getCountries()) || hasValues(filters.getRegions())) {
                List<Predicate> locationPredicates = new java.util.ArrayList<>();
                addOrLike(locationPredicates, cb, country.get("name"), filters.getCountries());
                addOrLike(locationPredicates, cb, country.get("regionWorld"), filters.getRegions());
                Predicate experienceLocationPredicate = experienceLocationPredicate(query, cb, root, filters.getCountries(), filters.getRegions());
                if (experienceLocationPredicate != null) locationPredicates.add(experienceLocationPredicate);
                if (!locationPredicates.isEmpty()) predicates.add(cb.or(locationPredicates.toArray(Predicate[]::new)));
            }

            inLike(predicates, cb, root.get("nationality"), filters.getNationality());
            inLike(predicates, cb, root.get("level"), values(filters.getSeniority()));
            inLike(predicates, cb, root.get("currentPosition"), values(filters.getCurrentlyWorkingIn()));
            inLike(predicates, cb, organization.get("name"), filters.getDatabases());

            if (filters.getMinProjects() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("completedProjects"), filters.getMinProjects().longValue()));
            }

            addExperienceSubquery(predicates, query, cb, root, filters);
            addEducationSubquery(predicates, query, cb, root, filters.getEducation());
            addLanguageSubquery(predicates, query, cb, root, filters);

            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }

    private Predicate keywordPredicate(
            Root<Expert> root,
            Join<Expert, User> user,
            Join<Expert, Country> country,
            Join<Expert, Organization> organization,
            Join<Expert, Sector> sector,
            CriteriaBuilder cb,
            String keyword
    ) {
        String value = likeValue(keyword);
        return cb.or(
                cb.like(cb.lower(root.get("fullName")), value),
                cb.like(cb.lower(root.get("profileSummary")), value),
                cb.like(cb.lower(root.get("bio")), value),
                cb.like(cb.lower(root.get("currentPosition")), value),
                cb.like(cb.lower(user.get("firstName")), value),
                cb.like(cb.lower(user.get("lastName")), value),
                cb.like(cb.lower(country.get("name")), value),
                cb.like(cb.lower(organization.get("name")), value),
                cb.like(cb.lower(sector.get("name")), value)
        );
    }

    private void addExperienceSubquery(List<Predicate> predicates, CriteriaQuery<?> query, CriteriaBuilder cb, Root<Expert> root, ExpertSearchRequest filters) {
        boolean needsExperience = hasValues(filters.getFundingAgencies())
                || hasText(filters.getTimeframeExperience());
        if (!needsExperience) return;

        Subquery<Long> subquery = query.subquery(Long.class);
        Root<ExpertExperience> experience = subquery.from(ExpertExperience.class);
        Join<ExpertExperience, Country> country = experience.join("country", JoinType.LEFT);
        Join<ExpertExperience, Sector> sector = experience.join("sector", JoinType.LEFT);
        List<Predicate> expPredicates = new java.util.ArrayList<>();
        expPredicates.add(cb.equal(experience.get("expert").get("id"), root.get("id")));
        addOrLike(expPredicates, cb, experience.get("donorName"), filters.getFundingAgencies());
        LocalDate startBoundary = timeframeBoundary(filters.getTimeframeExperience());
        if (startBoundary != null) {
            expPredicates.add(cb.or(
                    cb.greaterThanOrEqualTo(experience.get("startDate"), startBoundary),
                    cb.greaterThanOrEqualTo(experience.get("endDate"), startBoundary)
            ));
        }
        subquery.select(experience.get("expert").get("id")).where(cb.and(expPredicates.toArray(Predicate[]::new)));
        predicates.add(cb.exists(subquery));
    }

    private Predicate sectorPredicate(
            CriteriaQuery<?> query,
            CriteriaBuilder cb,
            Root<Expert> root,
            Join<Expert, Sector> mainSector,
            List<String> sectors,
            List<String> subSectors
    ) {
        List<Predicate> sectorPredicates = new java.util.ArrayList<>();
        addOrLike(sectorPredicates, cb, mainSector.get("name"), sectors);

        // Check in ExpertSubscriptionSector
        if (hasValues(sectors)) {
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<ExpertSubscriptionSector> subSector = subquery.from(ExpertSubscriptionSector.class);
            Join<ExpertSubscriptionSector, Sector> s = subSector.join("sector", JoinType.INNER);
            List<Predicate> subPredicates = new java.util.ArrayList<>();
            subPredicates.add(cb.equal(subSector.get("expert").get("id"), root.get("id")));
            addOrLike(subPredicates, cb, s.get("name"), sectors);
            subquery.select(subSector.get("expert").get("id")).where(cb.and(subPredicates.toArray(Predicate[]::new)));
            sectorPredicates.add(cb.exists(subquery));
        }

        if (hasValues(subSectors)) {
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<ExpertExperience> experience = subquery.from(ExpertExperience.class);
            Join<ExpertExperience, Sector> experienceSector = experience.join("sector", JoinType.LEFT);
            List<Predicate> expPredicates = new java.util.ArrayList<>();
            expPredicates.add(cb.equal(experience.get("expert").get("id"), root.get("id")));
            addOrLike(expPredicates, cb, experienceSector.get("name"), subSectors);
            addOrLike(expPredicates, cb, experience.get("description"), subSectors);
            subquery.select(experience.get("expert").get("id")).where(cb.and(expPredicates.toArray(Predicate[]::new)));
            sectorPredicates.add(cb.exists(subquery));
        }

        return sectorPredicates.isEmpty() ? null : cb.or(sectorPredicates.toArray(Predicate[]::new));
    }

    private Predicate experienceLocationPredicate(
            CriteriaQuery<?> query,
            CriteriaBuilder cb,
            Root<Expert> root,
            List<String> countries,
            List<String> regions
    ) {
        if (!hasValues(countries) && !hasValues(regions)) return null;

        Subquery<Long> subquery = query.subquery(Long.class);
        Root<ExpertExperience> experience = subquery.from(ExpertExperience.class);
        Join<ExpertExperience, Country> country = experience.join("country", JoinType.LEFT);
        List<Predicate> expPredicates = new java.util.ArrayList<>();
        expPredicates.add(cb.equal(experience.get("expert").get("id"), root.get("id")));
        addOrLike(expPredicates, cb, country.get("name"), countries);
        addOrLike(expPredicates, cb, country.get("regionWorld"), regions);
        subquery.select(experience.get("expert").get("id")).where(cb.and(expPredicates.toArray(Predicate[]::new)));
        return cb.exists(subquery);
    }

    private void addEducationSubquery(List<Predicate> predicates, CriteriaQuery<?> query, CriteriaBuilder cb, Root<Expert> root, List<String> educationValues) {
        if (!hasValues(educationValues)) return;

        Subquery<Long> subquery = query.subquery(Long.class);
        Root<ExpertEducation> education = subquery.from(ExpertEducation.class);
        List<Predicate> eduPredicates = new java.util.ArrayList<>();
        eduPredicates.add(cb.equal(education.get("expert").get("id"), root.get("id")));
        List<Predicate> matches = educationValues.stream()
                .filter(this::hasText)
                .map(value -> {
                    String like = likeValue(value);
                    return cb.or(
                            cb.like(cb.lower(education.get("fieldOfStudy")), like),
                            cb.like(cb.lower(education.get("degree")), like),
                            cb.like(cb.lower(education.get("institution")), like)
                    );
                })
                .toList();
        eduPredicates.add(cb.or(matches.toArray(Predicate[]::new)));
        subquery.select(education.get("expert").get("id")).where(cb.and(eduPredicates.toArray(Predicate[]::new)));
        predicates.add(cb.exists(subquery));
    }

    private void addLanguageSubquery(List<Predicate> predicates, CriteriaQuery<?> query, CriteriaBuilder cb, Root<Expert> root, ExpertSearchRequest filters) {
        if (!hasValues(filters.getLanguages()) && !hasText(filters.getLanguageLevel()) && !hasText(filters.getCvLanguage())) return;

        Subquery<Long> subquery = query.subquery(Long.class);
        Root<ExpertLanguage> language = subquery.from(ExpertLanguage.class);
        Join<ExpertLanguage, Language> languageCode = language.join("languageCode", JoinType.LEFT);
        List<Predicate> langPredicates = new java.util.ArrayList<>();
        langPredicates.add(cb.equal(language.get("expert").get("id"), root.get("id")));
        addOrLike(langPredicates, cb, languageCode.get("name"), filters.getLanguages());
        addOrLike(langPredicates, cb, languageCode.get("name"), values(filters.getCvLanguage()));
        addOrLike(langPredicates, cb, language.get("proficiency"), values(filters.getLanguageLevel()));
        subquery.select(language.get("expert").get("id")).where(cb.and(langPredicates.toArray(Predicate[]::new)));
        predicates.add(cb.exists(subquery));
    }

    private void like(List<Predicate> predicates, CriteriaBuilder cb, Path<String> path, String value) {
        if (hasText(value)) predicates.add(cb.like(cb.lower(path), likeValue(value)));
    }

    private void inLike(List<Predicate> predicates, CriteriaBuilder cb, Path<String> path, List<String> values) {
        addOrLike(predicates, cb, path, values);
    }

    private void addOrLike(List<Predicate> predicates, CriteriaBuilder cb, Path<String> path, List<String> values) {
        if (!hasValues(values)) return;
        List<Predicate> ors = values.stream()
                .filter(this::hasText)
                .map(value -> cb.like(cb.lower(path), likeValue(value)))
                .toList();
        if (!ors.isEmpty()) predicates.add(cb.or(ors.toArray(Predicate[]::new)));
    }

    private List<String> values(String value) {
        return hasText(value) ? List.of(value) : List.of();
    }

    private boolean hasValues(List<String> values) {
        return values != null && values.stream().anyMatch(this::hasText);
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isBlank() && !"all".equalsIgnoreCase(value.trim());
    }

    private String likeValue(String value) {
        return "%" + Objects.toString(value, "").trim().toLowerCase(Locale.ROOT) + "%";
    }

    private LocalDate timeframeBoundary(String timeframe) {
        if (!hasText(timeframe)) return null;
        String normalized = timeframe.toLowerCase(Locale.ROOT);
        if (normalized.contains("3")) return LocalDate.now().minusYears(3);
        if (normalized.contains("5")) return LocalDate.now().minusYears(5);
        if (normalized.contains("10") && !normalized.contains("more")) return LocalDate.now().minusYears(10);
        return null;
    }

    private final com.backend.assorttis.repository.ExpertSavedSearchRepository savedSearchRepository;
    private final com.backend.assorttis.repository.UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<com.backend.assorttis.dto.expert.ExpertSavedSearchDTO> getSavedSearches(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return savedSearchRepository.findByUserOrderByCreatedAtDesc(user).stream()
                .map(expertMapper::toSavedSearchDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public com.backend.assorttis.dto.expert.ExpertSavedSearchDTO saveSearch(Long userId, String name, java.util.Map<String, Object> payload) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        com.backend.assorttis.entities.ExpertSavedSearch savedSearch = new com.backend.assorttis.entities.ExpertSavedSearch()
                .setUser(user)
                .setName(name)
                .setPayload(payload)
                .setCreatedAt(java.time.Instant.now());
        return expertMapper.toSavedSearchDTO(savedSearchRepository.save(savedSearch));
    }

    @Transactional
    public void deleteSavedSearch(Long id) {
        savedSearchRepository.deleteById(id);
    }
}
