package com.backend.assorttis.service.impl;

import com.backend.assorttis.dto.training.CourseCatalogResponseDto;
import com.backend.assorttis.dto.training.CourseSectorSummaryDto;
import com.backend.assorttis.entities.Cours;
import com.backend.assorttis.entities.CourseSector;
import com.backend.assorttis.entities.Expert;
import com.backend.assorttis.entities.Sector;
import com.backend.assorttis.entities.User;
import com.backend.assorttis.repository.CoursRepository;
import com.backend.assorttis.repository.CourseSectorRepository;
import com.backend.assorttis.service.CourseCatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseCatalogServiceImpl implements CourseCatalogService {

    private final CoursRepository coursRepository;
    private final CourseSectorRepository courseSectorRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CourseCatalogResponseDto> getCatalogCourses() {

        List<Cours> courses = coursRepository.findPublishedCoursesForCatalog();

        if (courses.isEmpty()) {
            return List.of();
        }

        List<Long> courseIds = courses.stream()
                .map(Cours::getId)
                .filter(Objects::nonNull)
                .toList();

        Map<Long, List<CourseSectorSummaryDto>> sectorsByCourseId =
                courseSectorRepository.findByCourseIdsWithSector(courseIds)
                        .stream()
                        .collect(Collectors.groupingBy(
                                cs -> cs.getCourse().getId(),
                                Collectors.mapping(this::toSectorDto, Collectors.toList())
                        ));

        return courses.stream()
                .map(course -> toCatalogDto(course, sectorsByCourseId.getOrDefault(course.getId(), List.of())))
                .toList();
    }

    private CourseCatalogResponseDto toCatalogDto(Cours course, List<CourseSectorSummaryDto> sectors) {
        Expert expert = course.getExpert();

        return new CourseCatalogResponseDto()
                .setId(course.getId())
                .setTitle(course.getTitle())
                .setDescription(course.getDescription())
                .setLevel(course.getLevel())
                .setDeliveryMode(course.getDeliveryMode())
                .setDurationHours(course.getDurationHours())
                .setModulesCount(course.getModulesCount())
                .setStartDate(course.getStartDate())
                .setCourseLanguage(course.getCourseLanguage())
                .setPrice(course.getPrice())
                .setCurrency(course.getCurrency())
                .setIsFree(course.getIsFree())
                .setStatus(course.getStatus())
                .setThumbnailUrl(course.getThumbnailUrl())
                .setCertificationAvailable(course.getCertificationAvailable())
                .setCertificationPrice(course.getCertificationPrice())
                .setCertificationTitle(course.getCertificationTitle())
                .setCertificationIssuer(course.getCertificationIssuer())
                .setCertificationValidityMonths(course.getCertificationValidityMonths())
                .setTags(course.getTags())
                .setExpertId(expert != null ? expert.getId() : null)
                .setExpertName(resolveExpertName(expert))
                .setSectors(sectors)
                .setCreatedAt(course.getCreatedAt());
    }

    private CourseSectorSummaryDto toSectorDto(CourseSector courseSector) {
        Sector sector = courseSector.getSector();

        if (sector == null) {
            return null;
        }

        return new CourseSectorSummaryDto(
                sector.getId(),
                sector.getCode(),
                sector.getName()
        );
    }

    private String resolveExpertName(Expert expert) {
        if (expert == null) {
            return null;
        }

        if (expert.getFullName() != null && !expert.getFullName().isBlank()) {
            return expert.getFullName();
        }

        User user = expert.getUser();

        if (user == null) {
            return null;
        }

        String firstName = user.getFirstName() != null ? user.getFirstName() : "";
        String lastName = user.getLastName() != null ? user.getLastName() : "";

        String fullName = (firstName + " " + lastName).trim();

        return fullName.isBlank() ? user.getEmail() : fullName;
    }
}
