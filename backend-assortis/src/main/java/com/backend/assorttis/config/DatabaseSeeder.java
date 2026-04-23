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
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (userRepository.count() > 0) {
            return; // Already seeded
        }

        // 1. Create Roles
        Role expertRole = createRole("EXPERT", "Expert", "Role for individual experts");
        Role orgRole = createRole("ORGANIZATION", "Organization", "Role for organizations");
        Role adminRole = createRole("ADMIN", "Administrator", "System Administrator role");

        // 2. Create Permissions
        Permission readOffers = createPermission("READ_OFFERS", "Can read offers");
        Permission writeOffers = createPermission("WRITE_OFFERS", "Can publish offers");

        assignPermissionToRole(expertRole, readOffers);
        assignPermissionToRole(orgRole, readOffers);
        assignPermissionToRole(orgRole, writeOffers);

        // 3. Create Test Users
        User expertUser = createUser("expert@example.com", "expert123", "John", "Expert", expertRole);
        User orgUser = createUser("organization@example.com", "org123", "Jane", "Org", orgRole);
        createUser("admin@example.com", "admin123", "System", "Admin", adminRole);

        // 4. Create Expert Profile
        Expert expert = new Expert()
                .setUser(expertUser)
                .setFullName("John Expert")
                .setYearsExperience(10)
                .setAvailabilityStatus("AVAILABLE")
                .setCreatedAt(Instant.now());
        expert = expertRepository.save(expert);

        // 5. Create Organization
        Organization organization = new Organization()
                .setName("Assortis Tech")
                .setLegalName("Assortis Technologies Ltd.")
                .setType("IT_SERVICES")
                .setIsActive(true)
                .setCreatedAt(Instant.now());
        organization = organizationRepository.save(organization);

        // Link Org User to Organization
        OrganizationUser orgUserMembership = new OrganizationUser();
        OrganizationUserId orgUserId = new OrganizationUserId()
                .setOrganizationId(organization.getId())
                .setUserId(orgUser.getId());
        orgUserMembership.setId(orgUserId);
        orgUserMembership.setOrganization(organization);
        orgUserMembership.setUser(orgUser);
        orgUserMembership.setMembershipStatus("active");
        orgUserMembership.setDepartment("Human Resources");
        orgUserMembership.setIsAdmin(true);
        orgUserMembership.setJoinedAt(Instant.now());
        organizationUserRepository.save(orgUserMembership);

        // 6. Create Projects
        Project project1 = new Project()
                .setTitle("Global Infrastructure Overhaul")
                .setStatus("ACTIVE")
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

    private Role createRole(String code, String label, String description) {
        Role role = new Role()
                .setCode(code)
                .setLabel(label)
                .setDescription(description)
                .setIsSystem(true);
        return roleRepository.save(role);
    }

    private Permission createPermission(String code, String description) {
        Permission permission = new Permission()
                .setCode(code)
                .setDescription(description);
        return permissionRepository.save(permission);
    }

    private void assignPermissionToRole(Role role, Permission permission) {
        RolePermission rolePermission = new RolePermission();
        RolePermissionId id = new RolePermissionId()
                .setRoleId(role.getId())
                .setPermissionId(permission.getId());
        rolePermission.setId(id);
        rolePermission.setRole(role);
        rolePermission.setPermission(permission);
        rolePermissionRepository.save(rolePermission);
    }

    private User createUser(String email, String password, String firstName, String lastName, Role role) {
        User user = new User()
                .setEmail(email)
                .setPasswordHash(passwordEncoder.encode(password))
                .setFirstName(firstName)
                .setLastName(lastName)
                .setIsActive(true)
                .setEmailVerified(true)
                .setCreatedAt(Instant.now());

        user = userRepository.save(user);

        UserRole userRole = new UserRole();
        UserRoleId id = new UserRoleId()
                .setUserId(user.getId())
                .setRoleId(role.getId())
                .setScopeType("GLOBAL")
                .setScopeId(0L);
        userRole.setId(id);
        userRole.setUser(user);
        userRole.setRole(role);
        userRoleRepository.save(userRole);
        return user;
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
