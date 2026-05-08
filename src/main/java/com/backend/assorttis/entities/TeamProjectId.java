package com.backend.assorttis.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Embeddable
public class TeamProjectId implements Serializable {
    private static final long serialVersionUID = 9063613490835940182L;
    private Long teamId;

    private Long projectId;

    @NotNull
    @Column(name = "team_id", nullable = false)
    public Long getTeamId() {
        return teamId;
    }

    @NotNull
    @Column(name = "project_id", nullable = false)
    public Long getProjectId() {
        return projectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        TeamProjectId entity = (TeamProjectId) o;
        return Objects.equals(this.teamId, entity.teamId) &&
                Objects.equals(this.projectId, entity.projectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamId, projectId);
    }

}
