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
@Table(name = "my_projects", schema = "public")
public class MyProject {
    private Long id;

    private User user;

    private String projectType;

    private Long projectId;

    private String notes;

    private Instant addedAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    @Size(max = 50)
    @Column(name = "project_type", length = 50)
    public String getProjectType() {
        return projectType;
    }

    @Column(name = "project_id")
    public Long getProjectId() {
        return projectId;
    }

    @Column(name = "notes", length = Integer.MAX_VALUE)
    public String getNotes() {
        return notes;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "added_at")
    public Instant getAddedAt() {
        return addedAt;
    }

}
