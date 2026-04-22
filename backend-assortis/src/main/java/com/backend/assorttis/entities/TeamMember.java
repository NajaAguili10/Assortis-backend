package com.backend.assorttis.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "team_members", schema = "public")
public class TeamMember {
    private TeamMemberId id;

    private Team team;

    private User user;

    private String role;

    private Instant joinedAt;

    @EmbeddedId
    public TeamMemberId getId() {
        return id;
    }

    @MapsId("teamId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "team_id", nullable = false)
    public Team getTeam() {
        return team;
    }

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    public User getUser() {
        return user;
    }

    @Size(max = 100)
    @Column(name = "role", length = 100)
    public String getRole() {
        return role;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "joined_at")
    public Instant getJoinedAt() {
        return joinedAt;
    }

}
