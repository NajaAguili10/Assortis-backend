package com.backend.assorttis.config;

import com.backend.assorttis.entities.*;
import com.backend.assorttis.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.core.annotation.Order;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.backend.assorttis.entities.enums.project.ProjectStatus.ACTIVE;

@Component
@RequiredArgsConstructor
@Order(1)
public class DatabaseSeeder implements CommandLineRunner {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PermissionRepository permissionRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final OrganizationRepository organizationRepository;
    private final OrganizationUserRepository organizationUserRepository;
    private final JobOfferRepository jobOfferRepository;
    private final ExpertRepository expertRepository;
    private final InvitationRepository invitationRepository;
    private final ProjectRepository projectRepository;
    private final JobApplicationRepository jobApplicationRepository;
    private final ExpertSubscriptionSectorRepository expertSubscriptionSectorRepository;
    private final ExpertSubscriptionCountryRepository expertSubscriptionCountryRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (projectRepository.count() > 0) {
            return; // Already seeded projects
        }

        // 0. Cleanup removed to allow testing persistent registrations

        // 1. Create Roles (Idempotent)
        Role expertRole = ensureRole("EXPERT", "Expert", "Role for individual experts");
        Role orgRole = ensureRole("ORGANIZATION", "Organization", "Role for organizations");
        Role adminRole = ensureRole("ADMIN", "Administrator", "System Administrator role");

        // 2. Create Permissions
        Permission readOffers = createPermission("READ_OFFERS", "Can read offers");
        Permission writeOffers = createPermission("WRITE_OFFERS", "Can publish offers");

        assignPermissionToRole(expertRole, readOffers);
        assignPermissionToRole(orgRole, readOffers);
        assignPermissionToRole(orgRole, writeOffers);

        // 3. Create Test Users (Idempotent)
        User expertUser = ensureUser("expert@example.com", "password123", "John", "Expert", expertRole);
        User orgUser = ensureUser("organization@example.com", "password123", "Jane", "Org", orgRole);
        ensureUser("admin@example.com", "password123", "System", "Admin", adminRole);

        // 4. Create Expert Profile
        Expert expert = new Expert();
        expert.setUser(expertUser);
        expert.setFullName("John Expert");
        expert.setYearsExperience(10);
        expert.setAvailabilityStatus("available");
        expert.setCreatedAt(Instant.now());
        expert = expertRepository.save(expert);

        // 5. Create Organization
        Organization organization = new Organization();
        organization.setName("Assortis Tech");
        organization.setLegalName("Assortis Technologies Ltd.");
        organization.setType("IT_SERVICES");
        organization.setIsActive(true);
        organization.setCreatedAt(Instant.now());
        organization = organizationRepository.save(organization);

        // Link Org User to Organization 19 (instead of the newly created one, to match experts)
        Organization targetOrgForUser = organizationRepository.findById(19L).orElse(organization);

        OrganizationUser orgUserMembership = new OrganizationUser();
        OrganizationUserId orgUserId = new OrganizationUserId();
        orgUserId.setOrganizationId(targetOrgForUser.getId());
        orgUserId.setUserId(orgUser.getId());
        orgUserMembership.setId(orgUserId);
        orgUserMembership.setOrganization(targetOrgForUser);
        orgUserMembership.setUser(orgUser);
        orgUserMembership.setMembershipStatus("active");
        orgUserMembership.setDepartment("Human Resources");
        orgUserMembership.setIsAdmin(true);
        orgUserMembership.setJoinedAt(Instant.now());
        organizationUserRepository.save(orgUserMembership);

        // New relationship: Org 7 and User 7 (as requested)
        organizationRepository.findById(7L).ifPresent(org7 -> {
            userRepository.findById(7L).ifPresent(user7 -> {
                OrganizationUserId id77 = new OrganizationUserId();
                id77.setOrganizationId(7L);
                id77.setUserId(7L);

                if (!organizationUserRepository.existsById(id77)) {
                    OrganizationUser membership77 = new OrganizationUser();
                    membership77.setId(id77);
                    membership77.setOrganization(org7);
                    membership77.setUser(user7);
                    membership77.setMembershipStatus("active");
                    membership77.setJoinedAt(Instant.now());
                    organizationUserRepository.save(membership77);
                }
            });
        });

        // 6. Create Projects
        Project project1 = new Project()
                .setTitle("Global Infrastructure Overhaul")
                .setStatus(ACTIVE)
                .setReferenceCode("P-2026-001")
                .setUpdatedAt(Instant.now());
        project1 = projectRepository.save(project1);

        // 7. Create Job Offers for Organization
        // Active - Project - Closing Soon
        createJobOffer(organization, project1, "Senior Cloud Architect", "PUBLISHED", LocalDate.now().plusDays(3));
        // Active - Project - Not Closing Soon
        createJobOffer(organization, project1, "DevOps Engineer", "PUBLISHED", LocalDate.now().plusDays(15));
        // Active - Internal - Closing Soon
        createJobOffer(organization, null, "Office Manager", "PUBLISHED", LocalDate.now().plusDays(5));
        // Active - Internal - Not Closing Soon
        createJobOffer(organization, null, "HR Coordinator", "PUBLISHED", LocalDate.now().plusDays(20));
        // Inactive / Draft
        createJobOffer(organization, null, "Junior Developer", "DRAFT", LocalDate.now().plusDays(30));

        // 8. Create Job Applications
        List<JobOffer> publishedOffers = jobOfferRepository.findAll();
        for (JobOffer offer : publishedOffers) {
            if ("PUBLISHED".equals(offer.getStatus())) {
                JobApplication app = new JobApplication()
                        .setJobOffer(offer)
                        .setExpert(expert)
                        .setStatus("PENDING")
                        .setAppliedAt(Instant.now());
                jobApplicationRepository.save(app);
            }
        }

        // 9. Create Invitations
        // Received by Expert
        createInvitation(null, orgUser, expert, "DIRECT", "We want you for our project!", "PENDING", organization);
        createInvitation(null, null, expert, "SYSTEM", "Automated invitation", "ACCEPTED", null);

        // Sent by Expert (user)
        createInvitation(null, expertUser, null, "PARTNERSHIP", "Expert seeking collaboration", "PENDING", null);
    }

    private Role ensureRole(String code, String label, String description) {
        return roleRepository.findByCode(code).orElseGet(() -> {
            Role role = new Role()
                    .setCode(code)
                    .setLabel(label)
                    .setDescription(description)
                    .setIsSystem(true);
            return roleRepository.save(role);
        });
    }

    private Permission createPermission(String code, String description) {
        return permissionRepository.findByCode(code).orElseGet(() -> {
            Permission permission = new Permission()
                    .setCode(code)
                    .setDescription(description);
            return permissionRepository.save(permission);
        });
    }

    private void assignPermissionToRole(Role role, Permission permission) {
        RolePermissionId id = new RolePermissionId()
                .setRoleId(role.getId())
                .setPermissionId(permission.getId());

        if (!rolePermissionRepository.existsById(id)) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setId(id);
            rolePermission.setRole(role);
            rolePermission.setPermission(permission);
            rolePermissionRepository.save(rolePermission);
        }
    }

    private User ensureUser(String email, String password, String firstName, String lastName, Role role) {
        return userRepository.findByEmail(email).map(user -> {
            user.setPasswordHash(passwordEncoder.encode(password));
            userRepository.save(user);
            assignRoleIfMissing(user, role);
            return user;
        }).orElseGet(() -> {
            User user = new User()
                    .setEmail(email)
                    .setPasswordHash(passwordEncoder.encode(password))
                    .setFirstName(firstName)
                    .setLastName(lastName)
                    .setIsActive(true)
                    .setEmailVerified(true)
                    .setCreatedAt(Instant.now());

            user = userRepository.save(user);
            assignRoleIfMissing(user, role);
            return user;
        });
    }

    private void assignRoleIfMissing(User user, Role role) {
        UserRoleId id = new UserRoleId()
                .setUserId(user.getId())
                .setRoleId(role.getId())
                .setScopeType("GLOBAL")
                .setScopeId(0L);

        if (!userRoleRepository.existsById(id)) {
            UserRole userRole = new UserRole();
            userRole.setId(id);
            userRole.setUser(user);
            userRole.setRole(role);
            userRole.setAssignedAt(Instant.now());
            userRoleRepository.save(userRole);
        }
    }

    private void createJobOffer(Organization org, Project project, String title, String status, LocalDate deadline) {
        JobOffer offer = new JobOffer()
                .setOrganization(org)
                .setProject(project)
                .setTitle(title)
                .setStatus(status)
                .setDeadline(deadline)
                .setVisibility(true)
                .setCreatedAt(Instant.now());
        jobOfferRepository.save(offer);
    }

    private void createInvitation(Tender tender, User inviter, Expert invitee, String type, String message,
                                  String status, Organization inviterOrg) {
        Invitation invitation = new Invitation()
                .setTender(tender)
                .setInviter(inviter)
                .setInvitee(invitee)
                .setInvitationType(type)
                .setMessage(message)
                .setStatus(status)
                .setInviterOrganization(inviterOrg)
                .setCreatedAt(Instant.now())
                .setExpiresAt(Instant.now().plus(7, ChronoUnit.DAYS));
        invitationRepository.save(invitation);
    }
}
