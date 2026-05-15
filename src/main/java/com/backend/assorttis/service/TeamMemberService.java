package com.backend.assorttis.service;

import com.backend.assorttis.dto.organization.OrganizationTeamInvitationRequest;
import com.backend.assorttis.dto.organization.OrganizationTeamMemberUpdateRequest;
import com.backend.assorttis.dto.organization.OrganizationTeamMembersDTO;
import com.backend.assorttis.entities.Expert;
import com.backend.assorttis.entities.Invitation;
import com.backend.assorttis.entities.Organization;
import com.backend.assorttis.entities.OrganizationUser;
import com.backend.assorttis.entities.User;
import com.backend.assorttis.repository.ExpertRepository;
import com.backend.assorttis.repository.InvitationRepository;
import com.backend.assorttis.repository.OrganizationUserRepository;
import com.backend.assorttis.repository.TeamMemberRepository;
import com.backend.assorttis.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class TeamMemberService {
    private final OrganizationUserRepository organizationUserRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final ExpertRepository expertRepository;
    private final UserRepository userRepository;
    private final InvitationRepository invitationRepository;

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

    @Transactional(readOnly = true)
    public List<String> getAvailableDepartmentsForUser(Long userId) {
        Organization organization = resolveCurrentOrganization(userId);

        Set<String> departments = new LinkedHashSet<>();
        organizationUserRepository.findDistinctDepartmentsByOrganizationId(organization.getId()).stream()
                .map(this::normalizeDepartment)
                .filter(StringUtils::hasText)
                .forEach(departments::add);
        organizationUserRepository.findDistinctDepartments().stream()
                .map(this::normalizeDepartment)
                .filter(StringUtils::hasText)
                .forEach(departments::add);

        return departments.stream().toList();
    }

    @Transactional
    public OrganizationTeamMembersDTO.TeamMemberDTO updateCurrentOrganizationMember(
            Long actingUserId,
            Long memberUserId,
            OrganizationTeamMemberUpdateRequest request
    ) {
        Organization organization = resolveCurrentOrganization(actingUserId);

        OrganizationUser member = organizationUserRepository
                .findMemberByOrganizationIdAndUserId(organization.getId(), memberUserId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Organization member not found"));

        String normalizedRole = normalizeRole(request.getRole());
        member.setRole(normalizedRole);
        member.setIsAdmin("admin".equals(normalizedRole));
        member.setDepartment(blankToNull(request.getDepartment()));
        member.setMembershipStatus(normalizeStatus(request.getStatus()));

        OrganizationUser savedMember = organizationUserRepository.save(member);
        return toDTO(savedMember);
    }

    @Transactional
    public Long inviteCurrentOrganizationExpert(Long actingUserId, OrganizationTeamInvitationRequest request) {
        Organization organization = resolveCurrentOrganization(actingUserId);
        User inviter = userRepository.findById(actingUserId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Inviter not found"));

        if (request.getExpertId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Expert is required");
        }

        Expert expert = expertRepository.findById(request.getExpertId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Expert not found"));
        String expertEmail = expert.getUser() == null ? null : expert.getUser().getEmail();
        String requestedEmail = normalizeEmail(request.getEmail());

        if (!StringUtils.hasText(expertEmail) || !StringUtils.hasText(requestedEmail)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Expert email is required");
        }

        if (!normalizeEmail(expertEmail).equals(requestedEmail)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invitation email does not match selected expert");
        }

        Invitation invitation = new Invitation()
                .setInviter(inviter)
                .setInvitee(expert)
                .setInviterOrganization(organization)
                .setInvitationType("TEAM_MEMBER")
                .setMessage(blankToNull(request.getMessage()))
                .setMemberRole(normalizeRole(request.getRole()))
                .setMemberDepartment(blankToNull(request.getDepartment()))
                .setMemberStatus("active")
                .setStatus("PENDING")
                .setCreatedAt(Instant.now())
                .setExpiresAt(Instant.now().plus(14, ChronoUnit.DAYS));

        return invitationRepository.save(invitation).getId();
    }

    @Transactional
    public void deleteCurrentOrganizationMember(Long actingUserId, Long memberUserId) {
        Organization organization = resolveCurrentOrganization(actingUserId);

        OrganizationUser member = organizationUserRepository
                .findMemberByOrganizationIdAndUserId(organization.getId(), memberUserId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Organization member not found"));

        organizationUserRepository.delete(member);
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

    private String normalizeRole(String role) {
        String normalizedRole = nullToEmpty(role).trim().toLowerCase();
        return switch (normalizedRole) {
            case "admin", "member", "viewer" -> normalizedRole;
            default -> "member";
        };
    }

    private String normalizeStatus(String status) {
        String normalizedStatus = nullToEmpty(status).trim().toLowerCase();
        return switch (normalizedStatus) {
            case "pending", "inactive", "active" -> normalizedStatus;
            default -> "active";
        };
    }

    private String blankToNull(String value) {
        return StringUtils.hasText(value) ? value.trim() : null;
    }

    private String normalizeEmail(String value) {
        return StringUtils.hasText(value) ? value.trim().toLowerCase() : "";
    }

    private String normalizeDepartment(String department) {
        return StringUtils.hasText(department) ? department.trim() : null;
    }

    private String nullToEmpty(String value) {
        return value == null ? "" : value;
    }
}
