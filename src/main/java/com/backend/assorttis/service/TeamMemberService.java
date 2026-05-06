package com.backend.assorttis.service;

import com.backend.assorttis.dto.organization.OrganizationTeamMembersDTO;
import com.backend.assorttis.entities.Organization;
import com.backend.assorttis.entities.OrganizationUser;
import com.backend.assorttis.repository.OrganizationUserRepository;
import com.backend.assorttis.repository.TeamMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TeamMemberService {
    private final OrganizationUserRepository organizationUserRepository;
    private final TeamMemberRepository teamMemberRepository;

    @Transactional(readOnly = true)
    public OrganizationTeamMembersDTO getTeamMembersForUser(Long userId) {
        log.info("📥 userId :{}", userId);
        Organization organization = organizationUserRepository.findMembershipsByUserId(userId).stream()
                .filter(membership -> !"inactive".equalsIgnoreCase(nullToEmpty(membership.getMembershipStatus())))
                .map(OrganizationUser::getOrganization)
                .findFirst()
                .orElseGet(() -> teamMemberRepository.findMembershipsByUserId(userId).stream()
                        .map(teamMember -> teamMember.getTeam().getOrganization())
                        .findFirst()
                        .orElse(null));

        log.info("📥 organization ===> {}", organization);
        if (organization == null) {
            return OrganizationTeamMembersDTO.builder()
                    .organization(null)
                    .members(List.of())
                    .build();
        }

        log.info("📥 getTeamMembersForUser");
        log.info("USER ID: {}", userId);
        log.info("ORG FOUND: {}", organization != null ? organization.getId() : "NULL");

        return getTeamMembersForOrganization(organization.getId());
    }

    @Transactional(readOnly = true)
    public OrganizationTeamMembersDTO getTeamMembersForOrganization(Long organizationId) {
        List<OrganizationUser> organizationUsers = organizationUserRepository.findMembersByOrganizationId(organizationId);
        if (!organizationUsers.isEmpty()) {
            Organization organization = organizationUsers.get(0).getOrganization();

            log.info("📥 getTeamMembersForOrganization");
            log.info("organization ID: {}", organizationId);
            log.info("ORG FOUND: {}", organization != null ? organization.getId() : "NULL");

            return OrganizationTeamMembersDTO.builder()
                    .organization(toOrganizationSummary(organization))
                    .members(organizationUsers.stream()
                            .sorted(Comparator.comparing(
                                    OrganizationUser::getJoinedAt,
                                    Comparator.nullsLast(Comparator.naturalOrder())))
                            .map(this::toDTO)
                            .toList())
                    .build();
        }

        List<com.backend.assorttis.entities.TeamMember> teamMembers =
                teamMemberRepository.findMembersByOrganizationId(organizationId);
        Organization organization = teamMembers.isEmpty() ? null : teamMembers.get(0).getTeam().getOrganization();

        return OrganizationTeamMembersDTO.builder()
                .organization(organization == null
                        ? OrganizationTeamMembersDTO.OrganizationSummaryDTO.builder().id(organizationId).build()
                        : toOrganizationSummary(organization))
                .members(teamMembers.stream()
                        .sorted(Comparator.comparing(
                                com.backend.assorttis.entities.TeamMember::getJoinedAt,
                                Comparator.nullsLast(Comparator.naturalOrder())))
                        .map(this::toDTO)
                        .toList())
                .build();
    }

    private OrganizationTeamMembersDTO.OrganizationSummaryDTO toOrganizationSummary(Organization organization) {
        return OrganizationTeamMembersDTO.OrganizationSummaryDTO.builder()
                .id(organization.getId())
                .name(organization.getName())
                .type(organization.getType())
                .build();
    }

    private OrganizationTeamMembersDTO.TeamMemberDTO toDTO(OrganizationUser member) {
        return OrganizationTeamMembersDTO.TeamMemberDTO.builder()
                .userId(member.getUser().getId())
                .organizationId(member.getOrganization().getId())
                .firstName(member.getUser().getFirstName())
                .lastName(member.getUser().getLastName())
                .email(member.getUser().getEmail())
                .role(member.getRole())
                .admin(member.getIsAdmin())
                .department(member.getDepartment())
                .status(member.getMembershipStatus())
                .joinedAt(member.getJoinedAt())
                .build();
    }

    private OrganizationTeamMembersDTO.TeamMemberDTO toDTO(com.backend.assorttis.entities.TeamMember member) {
        return OrganizationTeamMembersDTO.TeamMemberDTO.builder()
                .userId(member.getUser().getId())
                .organizationId(member.getTeam().getOrganization().getId())
                .firstName(member.getUser().getFirstName())
                .lastName(member.getUser().getLastName())
                .email(member.getUser().getEmail())
                .role(member.getRole())
                .admin("MANAGER".equalsIgnoreCase(nullToEmpty(member.getRole()))
                        || "ADMIN".equalsIgnoreCase(nullToEmpty(member.getRole())))
                .department("")
                .status(Boolean.FALSE.equals(member.getUser().getIsActive()) ? "inactive" : "active")
                .joinedAt(member.getJoinedAt())
                .build();
    }

    private String nullToEmpty(String value) {
        return value == null ? "" : value;
    }
}
