package com.backend.assorttis.service.impl;

import com.backend.assorttis.dto.dashboard.DashboardStatsDTO;
import com.backend.assorttis.entities.*;
import com.backend.assorttis.repository.*;
import com.backend.assorttis.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final JobOfferRepository jobOfferRepository;
    private final JobApplicationRepository jobApplicationRepository;
    private final InvitationRepository invitationRepository;
    private final ExpertRepository expertRepository;
    private final OrganizationUserRepository organizationUserRepository;

    @Override
    public DashboardStatsDTO getDashboardStats(Long userId) {
        // Find associated Expert
        Expert expert = expertRepository.findOne((root, query, cb) -> cb.equal(root.get("user").get("id"), userId)).orElse(null);
        
        // Find associated Organizations
        List<OrganizationUser> orgUsers = organizationUserRepository.findAll((root, query, cb) -> 
            cb.and(
                cb.equal(root.get("user").get("id"), userId),
                cb.equal(root.get("membershipStatus"), "active")
            ));
        
        List<Long> orgIds = orgUsers.stream()
                .map(ou -> ou.getOrganization().getId())
                .collect(Collectors.toList());

        // 1. Global Stats
        long totalOffers = orgIds.isEmpty() ? 0 : jobOfferRepository.count((root, query, cb) -> 
                root.get("organization").get("id").in(orgIds));
        
        long activeOffers = orgIds.isEmpty() ? 0 : jobOfferRepository.count((root, query, cb) -> 
                cb.and(root.get("organization").get("id").in(orgIds), cb.equal(root.get("status"), "PUBLISHED")));
        
        long totalApplications = orgIds.isEmpty() ? 0 : jobApplicationRepository.count((root, query, cb) -> 
                root.get("jobOffer").get("organization").get("id").in(orgIds));
        
        LocalDate sevenDaysFromNow = LocalDate.now().plusDays(7);
        long closingSoonGlobal = orgIds.isEmpty() ? 0 : jobOfferRepository.count((root, query, cb) -> 
                cb.and(
                    root.get("organization").get("id").in(orgIds),
                    cb.equal(root.get("status"), "PUBLISHED"),
                    cb.lessThanOrEqualTo(root.get("deadline"), sevenDaysFromNow),
                    cb.greaterThanOrEqualTo(root.get("deadline"), LocalDate.now())
                ));

        // 2. Project Offers Stats
        long activeProjects = orgIds.isEmpty() ? 0 : jobOfferRepository.count((root, query, cb) -> 
                cb.and(
                    root.get("organization").get("id").in(orgIds),
                    cb.equal(root.get("status"), "PUBLISHED"),
                    cb.isNotNull(root.get("project"))
                ));
        long closingSoonProjects = orgIds.isEmpty() ? 0 : jobOfferRepository.count((root, query, cb) -> 
                cb.and(
                    root.get("organization").get("id").in(orgIds),
                    cb.equal(root.get("status"), "PUBLISHED"),
                    cb.isNotNull(root.get("project")),
                    cb.lessThanOrEqualTo(root.get("deadline"), sevenDaysFromNow),
                    cb.greaterThanOrEqualTo(root.get("deadline"), LocalDate.now())
                ));

        // 3. Internal Offers Stats
        long activeInternal = orgIds.isEmpty() ? 0 : jobOfferRepository.count((root, query, cb) -> 
                cb.and(
                    root.get("organization").get("id").in(orgIds),
                    cb.equal(root.get("status"), "PUBLISHED"),
                    cb.isNull(root.get("project"))
                ));
        long departmentsCount = orgUsers.stream()
                .map(OrganizationUser::getDepartment)
                .filter(d -> d != null && !d.isEmpty())
                .distinct()
                .count();

        // 4. Invitation Stats
        long invitationsReceived = expert == null ? 0 : invitationRepository.count((root, query, cb) -> 
                cb.equal(root.get("invitee").get("id"), expert.getId()));
        
        long invitationsPending = expert == null ? 0 : invitationRepository.count((root, query, cb) -> 
                cb.and(
                    cb.equal(root.get("invitee").get("id"), expert.getId()), 
                    cb.equal(root.get("status"), "PENDING")
                ));
        
        long invitationsSent = invitationRepository.count((root, query, cb) -> 
                cb.equal(root.get("inviter").get("id"), userId));

        return DashboardStatsDTO.builder()
                .global(DashboardStatsDTO.GlobalStatsDTO.builder()
                        .totalOffers(totalOffers)
                        .activeOffers(activeOffers)
                        .totalApplications(totalApplications)
                        .closingSoon(closingSoonGlobal)
                        .build())
                .projectOffers(DashboardStatsDTO.ProjectOffersStatsDTO.builder()
                        .active(activeProjects)
                        .closingSoon(closingSoonProjects)
                        .build())
                .internalOffers(DashboardStatsDTO.InternalOffersStatsDTO.builder()
                        .active(activeInternal)
                        .departments(departmentsCount)
                        .build())
                .myOffers(DashboardStatsDTO.MyOffersStatsDTO.builder()
                        .published(activeOffers)
                        .applications(totalApplications)
                        .build())
                .invitations(DashboardStatsDTO.InvitationStatsDTO.builder()
                        .received(invitationsReceived)
                        .pending(invitationsPending)
                        .sent(invitationsSent)
                        .build())
                .build();
    }
}
