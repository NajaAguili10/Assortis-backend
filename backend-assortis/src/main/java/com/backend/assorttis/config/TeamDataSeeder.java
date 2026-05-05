package com.backend.assorttis.config;

import com.backend.assorttis.entities.Organization;
import com.backend.assorttis.entities.Team;
import com.backend.assorttis.entities.TeamMember;
import com.backend.assorttis.entities.TeamMemberId;
import com.backend.assorttis.entities.User;
import com.backend.assorttis.repository.OrganizationRepository;
import com.backend.assorttis.repository.TeamMemberRepository;
import com.backend.assorttis.repository.TeamRepository;
import com.backend.assorttis.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Component
@RequiredArgsConstructor
@Order(6)
public class TeamDataSeeder implements CommandLineRunner {

    private final OrganizationRepository organizationRepository;
    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final UserRepository userRepository;

    private final AtomicLong teamIdCounter = new AtomicLong(100);

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (teamRepository.count() > 0) {
            return;
        }

        List<Organization> organizations = organizationRepository.findAll();
        List<User> users = userRepository.findAll();

        if (organizations.isEmpty()) {
            return;
        }

        User defaultUser = users.isEmpty() ? null : users.get(0);

        for (Organization org : organizations) {
            Team team = new Team();
            team.setId(teamIdCounter.getAndIncrement());
            team.setOrganization(org);
            team.setName("Core Team - " + org.getName());
            team.setDescription("Main operational team for " + org.getName());
            team.setCreatedAt(Instant.now());
            team.setCreatedBy(defaultUser);

            teamRepository.save(team);

            if (defaultUser != null) {
                TeamMember member = new TeamMember();
                TeamMemberId memberId = new TeamMemberId();
                memberId.setTeamId(team.getId());
                memberId.setUserId(defaultUser.getId());
                
                member.setId(memberId);
                member.setTeam(team);
                member.setUser(defaultUser);
                member.setRole("MANAGER");
                member.setJoinedAt(Instant.now());

                teamMemberRepository.save(member);
            }
        }
    }
}
