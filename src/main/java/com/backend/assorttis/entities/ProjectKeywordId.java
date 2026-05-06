package com.backend.assorttis.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class ProjectKeywordId implements Serializable {
    private static final long serialVersionUID = 4189204779460467508L;
    private Long projectId;

    private String keyword;

    @NotNull
    @Column(name = "project_id", nullable = false)
    public Long getProjectId() {
        return projectId;
    }

    @Size(max = 100)
    @NotNull
    @Column(name = "keyword", nullable = false, length = 100)
    public String getKeyword() {
        return keyword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        ProjectKeywordId entity = (ProjectKeywordId) o;
        return Objects.equals(this.keyword, entity.keyword) &&
                Objects.equals(this.projectId, entity.projectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyword, projectId);
    }

}
