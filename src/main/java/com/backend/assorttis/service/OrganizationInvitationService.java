package com.backend.assorttis.service;

import com.backend.assorttis.dto.organization.OrganizationInvitationDTO;
import com.backend.assorttis.dto.organization.OrganizationInvitationStatsDTO;
import com.backend.assorttis.dto.organization.OrganizationInvitationUpdateRequest;
import com.backend.assorttis.entities.Expert;
import com.backend.assorttis.entities.Invitation;
import com.backend.assorttis.entities.Organization;
import com.backend.assorttis.entities.OrganizationUser;
import com.backend.assorttis.entities.OrganizationUserId;
import com.backend.assorttis.entities.User;
import com.backend.assorttis.repository.InvitationRepository;
import com.backend.assorttis.repository.OrganizationUserRepository;
import com.backend.assorttis.repository.TeamMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class OrganizationInvitationService {
    private final InvitationRepository invitationRepository;
    private final OrganizationUserRepository organizationUserRepository;
    private final TeamMemberRepository teamMemberRepository;

    @Transactional(readOnly = true)
    public List<OrganizationInvitationDTO> getCurrentOrganizationInvitations(Long userId) {
        return getCurrentUserScopedInvitationEntities(userId).stream()
                .map(invitation -> toDTO(invitation, resolveCurrentOrganizationId(userId).orElse(null), userId))
                .toList();
    }

    @Transactional(readOnly = true)
    public OrganizationInvitationStatsDTO getCurrentOrganizationStats(Long userId) {
        List<OrganizationInvitationDTO> invitations = getCurrentOrganizationInvitations(userId);

        long received = invitations.stream().filter(invitation -> "received".equals(invitation.getDirection())).count();
        long sent = invitations.stream().filter(invitation -> "sent".equals(invitation.getDirection())).count();
        long pending = invitations.stream().filter(invitation -> "pending".equals(invitation.getStatus())).count();
        long accepted = invitations.stream().filter(invitation -> "accepted".equals(invitation.getStatus())).count();
        long rejected = invitations.stream().filter(invitation -> "rejected".equals(invitation.getStatus())).count();
        long expired = invitations.stream().filter(invitation -> "expired".equals(invitation.getStatus())).count();

        return OrganizationInvitationStatsDTO.builder()
                .received(received)
                .sent(sent)
                .pending(pending)
                .accepted(accepted)
                .rejected(rejected)
                .expired(expired)
                .total(invitations.size())
                .build();
    }

    @Transactional
    public OrganizationInvitationDTO acceptInvitation(Long userId, Long invitationId) {
        Long organizationId = resolveCurrentOrganizationId(userId).orElse(null);
        Invitation invitation = findReceivedInvitation(invitationId, organizationId, userId);

        invitation.setStatus("ACCEPTED");
        invitation.setRespondedAt(Instant.now());
        Invitation savedInvitation = invitationRepository.save(invitation);
        createOrganizationMembershipForAcceptedExpert(savedInvitation);
        return toDTO(savedInvitation, organizationId, userId);
    }

    @Transactional
    public OrganizationInvitationDTO rejectInvitation(
            Long userId,
            Long invitationId,
            OrganizationInvitationUpdateRequest request
    ) {
        Long organizationId = resolveCurrentOrganizationId(userId).orElse(null);
        Invitation invitation = findReceivedInvitation(invitationId, organizationId, userId);

        String message = invitation.getMessage();
        if (StringUtils.hasText(request.getRejectionJustification())) {
            message = StringUtils.hasText(message)
                    ? message + "\n\nRejection reason: " + request.getRejectionJustification().trim()
                    : "Rejection reason: " + request.getRejectionJustification().trim();
        }

        invitation.setStatus("REJECTED");
        invitation.setMessage(message);
        invitation.setRespondedAt(Instant.now());
        return toDTO(invitationRepository.save(invitation), organizationId, userId);
    }

    @Transactional
    public void deleteInvitation(Long userId, Long invitationId) {
        Long organizationId = resolveCurrentOrganizationId(userId).orElse(null);
        Invitation invitation = organizationId == null
                ? invitationRepository.findExpertReceivedInvitation(invitationId, userId)
                    .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Invitation not found"))
                : invitationRepository.findOrganizationScopedInvitation(invitationId, organizationId)
                    .orElseGet(() -> invitationRepository.findExpertReceivedInvitation(invitationId, userId)
                            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Invitation not found")));
        invitationRepository.delete(invitation);
    }

    private List<Invitation> getCurrentUserScopedInvitationEntities(Long userId) {
        LinkedHashMap<Long, Invitation> invitationsById = new LinkedHashMap<>();

        resolveCurrentOrganizationId(userId).ifPresent(organizationId ->
                invitationRepository.findOrganizationScopedInvitations(organizationId)
                        .forEach(invitation -> invitationsById.put(invitation.getId(), invitation)));

        invitationRepository.findExpertReceivedInvitations(userId)
                .forEach(invitation -> invitationsById.put(invitation.getId(), invitation));

        return new ArrayList<>(invitationsById.values());
    }

    private Invitation findScopedInvitation(Long invitationId, Long organizationId) {
        return invitationRepository.findOrganizationScopedInvitation(invitationId, organizationId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Invitation not found"));
    }

    private void createOrganizationMembershipForAcceptedExpert(Invitation invitation) {
        if (invitation.getInvitee() == null
                || invitation.getInvitee().getUser() == null
                || invitation.getInviterOrganization() == null) {
            return;
        }

        Organization organization = invitation.getInviterOrganization();
        User user = invitation.getInvitee().getUser();

        Optional<OrganizationUser> existingMembership =
                organizationUserRepository.findMemberByOrganizationIdAndUserId(organization.getId(), user.getId());
        if (existingMembership.isPresent()) {
            OrganizationUser member = existingMembership.get();
            if ("inactive".equalsIgnoreCase(nullToEmpty(member.getMembershipStatus()))) {
                member.setMembershipStatus(resolveInvitationMemberStatus(invitation));
                organizationUserRepository.save(member);
            }
            return;
        }

        OrganizationUserId membershipId = new OrganizationUserId()
                .setOrganizationId(organization.getId())
                .setUserId(user.getId());

        String role = resolveInvitationMemberRole(invitation);
        OrganizationUser member = new OrganizationUser()
                .setId(membershipId)
                .setOrganization(organization)
                .setUser(user)
                .setRole(role)
                .setIsAdmin("admin".equals(role))
                .setDepartment(blankToNull(invitation.getMemberDepartment()))
                .setMembershipStatus(resolveInvitationMemberStatus(invitation))
                .setJoinedAt(Instant.now());

        organizationUserRepository.save(member);
    }

    private Invitation findReceivedInvitation(Long invitationId, Long organizationId, Long userId) {
        if (organizationId != null) {
            Optional<Invitation> organizationInvitation =
                    invitationRepository.findOrganizationScopedInvitation(invitationId, organizationId);
            if (organizationInvitation.isPresent()) {
                Invitation invitation = organizationInvitation.get();
                if (isReceivedByOrganization(invitation, organizationId)) {
                    return invitation;
                }
            }
        }

        return invitationRepository.findExpertReceivedInvitation(invitationId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Only received invitations can be updated"));
    }

    private boolean isReceivedByOrganization(Invitation invitation, Long organizationId) {
        Long inviteeOrganizationId = invitation.getInviteeOrganization() == null
                ? null
                : invitation.getInviteeOrganization().getId();
        return organizationId != null && organizationId.equals(inviteeOrganizationId);
    }

    private Optional<Long> resolveCurrentOrganizationId(Long userId) {
        try {
            return Optional.of(resolveCurrentOrganization(userId).getId());
        } catch (ResponseStatusException error) {
            if (NOT_FOUND.equals(error.getStatusCode())) {
                return Optional.empty();
            }
            throw error;
        }
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

    private OrganizationInvitationDTO toDTO(Invitation invitation, Long organizationId, Long userId) {
        boolean sent = invitation.getInviterOrganization() != null
                && organizationId != null
                && organizationId.equals(invitation.getInviterOrganization().getId())
                && !isReceivedByExpert(invitation, userId);
        User inviteeUser = invitation.getInvitee() == null ? null : invitation.getInvitee().getUser();

        return OrganizationInvitationDTO.builder()
                .id(invitation.getId())
                .direction(sent ? "sent" : "received")
                .from(resolveSenderName(invitation))
                .to(resolveRecipientName(invitation))
                .invitationType(normalizeInvitationType(invitation.getInvitationType()))
                .status(resolveStatus(invitation))
                .subject(resolveSubject(invitation))
                .message(invitation.getMessage())
                .inviteeExpertId(invitation.getInvitee() == null ? null : invitation.getInvitee().getId())
                .inviteeUserId(inviteeUser == null ? null : inviteeUser.getId())
                .inviteeEmail(inviteeUser == null ? null : inviteeUser.getEmail())
                .sentDate(invitation.getCreatedAt())
                .expiryDate(invitation.getExpiresAt())
                .build();
    }

    private boolean isReceivedByExpert(Invitation invitation, Long userId) {
        return invitation.getInvitee() != null
                && invitation.getInvitee().getUser() != null
                && userId.equals(invitation.getInvitee().getUser().getId());
    }

    private String resolveSenderName(Invitation invitation) {
        if (invitation.getInviterOrganization() != null) {
            return invitation.getInviterOrganization().getName();
        }
        User inviter = invitation.getInviter();
        if (inviter != null) {
            String name = joinName(inviter.getFirstName(), inviter.getLastName());
            return StringUtils.hasText(name) ? name : inviter.getEmail();
        }
        return "System";
    }

    private String resolveRecipientName(Invitation invitation) {
        if (invitation.getInviteeOrganization() != null) {
            return invitation.getInviteeOrganization().getName();
        }
        Expert invitee = invitation.getInvitee();
        if (invitee != null) {
            if (StringUtils.hasText(invitee.getFullName())) {
                return invitee.getFullName();
            }
            if (invitee.getUser() != null) {
                String name = joinName(invitee.getUser().getFirstName(), invitee.getUser().getLastName());
                return StringUtils.hasText(name) ? name : invitee.getUser().getEmail();
            }
        }
        return "";
    }

    private String resolveSubject(Invitation invitation) {
        if (invitation.getTender() != null && StringUtils.hasText(invitation.getTender().getTitle())) {
            return invitation.getTender().getTitle();
        }
        if (invitation.getProject() != null && StringUtils.hasText(invitation.getProject().getTitle())) {
            return invitation.getProject().getTitle();
        }
        return switch (normalizeInvitationType(invitation.getInvitationType())) {
            case "partnership" -> "Partnership Invitation";
            case "consortium" -> "Consortium Invitation";
            case "collaboration" -> "Collaboration Invitation";
            case "team" -> "Team Member Invitation";
            case "advisor" -> "Advisor Invitation";
            case "consultant" -> "Consultant Invitation";
            default -> "Organization Invitation";
        };
    }

    private String normalizeInvitationType(String invitationType) {
        String normalized = nullToEmpty(invitationType).trim().toUpperCase();
        return switch (normalized) {
            case "PARTNERSHIP" -> "partnership";
            case "CONSORTIUM" -> "consortium";
            case "COLLABORATION" -> "collaboration";
            case "TEAM", "TEAM_MEMBER" -> "team";
            case "ADVISOR" -> "advisor";
            case "CONSULTANT", "DIRECT" -> "consultant";
            default -> "collaboration";
        };
    }

    private String resolveInvitationMemberRole(Invitation invitation) {
        String normalized = nullToEmpty(invitation.getMemberRole()).trim().toLowerCase();
        return switch (normalized) {
            case "admin", "member", "viewer" -> normalized;
            default -> "member";
        };
    }

    private String resolveInvitationMemberStatus(Invitation invitation) {
        String normalized = nullToEmpty(invitation.getMemberStatus()).trim().toLowerCase();
        return switch (normalized) {
            case "pending", "inactive", "active" -> normalized;
            default -> "active";
        };
    }

    private String resolveStatus(Invitation invitation) {
        String normalized = nullToEmpty(invitation.getStatus()).trim().toUpperCase();
        if (("PENDING".equals(normalized) || normalized.isBlank())
                && invitation.getExpiresAt() != null
                && invitation.getExpiresAt().isBefore(Instant.now())) {
            return "expired";
        }
        return switch (normalized) {
            case "ACCEPTED" -> "accepted";
            case "DECLINED", "REJECTED" -> "rejected";
            case "EXPIRED" -> "expired";
            default -> "pending";
        };
    }

    private String joinName(String firstName, String lastName) {
        return (nullToEmpty(firstName) + " " + nullToEmpty(lastName)).trim();
    }

    private String blankToNull(String value) {
        return StringUtils.hasText(value) ? value.trim() : null;
    }

    private String nullToEmpty(String value) {
        return value == null ? "" : value;
    }
}
