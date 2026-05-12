package com.backend.assorttis.repository;

import com.backend.assorttis.entities.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InvitationRepository extends JpaRepository<Invitation, Long>, JpaSpecificationExecutor<Invitation> {
    @Query("""
            select i from Invitation i
            left join fetch i.inviterOrganization inviterOrg
            left join fetch i.inviteeOrganization inviteeOrg
            left join fetch i.inviter inviter
            left join fetch i.invitee invitee
            left join fetch invitee.user inviteeUser
            left join fetch i.tender tender
            left join fetch i.project project
            where inviterOrg.id = :organizationId or inviteeOrg.id = :organizationId
            order by i.createdAt desc
            """)
    List<Invitation> findOrganizationScopedInvitations(@Param("organizationId") Long organizationId);

    @Query("""
            select i from Invitation i
            left join fetch i.inviterOrganization inviterOrg
            left join fetch i.inviteeOrganization inviteeOrg
            left join fetch i.inviter inviter
            left join fetch i.invitee invitee
            left join fetch invitee.user inviteeUser
            left join fetch i.tender tender
            left join fetch i.project project
            where inviteeUser.id = :userId
            order by i.createdAt desc
            """)
    List<Invitation> findExpertReceivedInvitations(@Param("userId") Long userId);

    @Query("""
            select i from Invitation i
            left join fetch i.inviterOrganization inviterOrg
            left join fetch i.inviteeOrganization inviteeOrg
            where i.id = :id and (inviterOrg.id = :organizationId or inviteeOrg.id = :organizationId)
            """)
    java.util.Optional<Invitation> findOrganizationScopedInvitation(
            @Param("id") Long id,
            @Param("organizationId") Long organizationId
    );

    @Query("""
            select i from Invitation i
            left join fetch i.inviterOrganization inviterOrg
            left join fetch i.inviteeOrganization inviteeOrg
            left join fetch i.inviter inviter
            left join fetch i.invitee invitee
            left join fetch invitee.user inviteeUser
            where i.id = :id and inviteeUser.id = :userId
            """)
    java.util.Optional<Invitation> findExpertReceivedInvitation(
            @Param("id") Long id,
            @Param("userId") Long userId
    );
}
