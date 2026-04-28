package com.backend.assorttis.service.impl;

import com.backend.assorttis.dto.training.TrainingPortfolioStatsDTO;
import com.backend.assorttis.repository.EnrollmentRepository;
import com.backend.assorttis.repository.OrganizationRepository;
import com.backend.assorttis.repository.OrganizationUserRepository;
import com.backend.assorttis.repository.UserCertificationRepository;
import com.backend.assorttis.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService {

    private final OrganizationRepository organizationRepository;
    private final OrganizationUserRepository organizationUserRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final UserCertificationRepository userCertificationRepository;

    @Override
    @Transactional(readOnly = true)
    public TrainingPortfolioStatsDTO getTrainingPortfolioStats(Long organizationId) {

        if (organizationId == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "organizationId is required"
            );
        }

        boolean organizationExists = organizationRepository.existsById(organizationId);

        if (!organizationExists) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Organization not found with id: " + organizationId
            );
        }

        /*
         * IMPORTANT:
         * We intentionally fetch ALL users belonging to the organization,
         * not only active users.
         *
         * Goal:
         * - completedTrainings = total completed trainings of all organization users
         * - certifications = total certification records of all organization users
         *
         * This means certifications count is COUNT(user_certifications rows),
         * not COUNT(DISTINCT users).
         */
        List<Long> userIds = organizationUserRepository.findUserIdsByOrganizationId(organizationId);

        if (userIds == null || userIds.isEmpty()) {
            return new TrainingPortfolioStatsDTO(0L, 0L, 0L);
        }

        Long completedTrainings = enrollmentRepository.countCompletedTrainingsByUserIds(userIds);
        Long certifications = userCertificationRepository.countByUserIds(userIds);

        return new TrainingPortfolioStatsDTO(
                completedTrainings != null ? completedTrainings : 0L,
                certifications != null ? certifications : 0L,
                0L
        );
    }
}
