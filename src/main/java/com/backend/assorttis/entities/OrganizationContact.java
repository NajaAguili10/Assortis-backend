package com.backend.assorttis.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "organization_contacts", schema = "public", indexes = {
        @Index(name = "idx_organization_contacts_org", columnList = "organization_id")
})
public class OrganizationContact {
    private Long id;

    private Organization organization;

    private String name;

    private String title;

    private String phone;

    private String email;

    private Boolean isPrimary;

    private String notes;

    private Instant createdAt;

    private Instant updatedAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "organization_id", nullable = false)
    public Organization getOrganization() {
        return organization;
    }

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    @Size(max = 255)
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    @Size(max = 50)
    @Column(name = "phone", length = 50)
    public String getPhone() {
        return phone;
    }

    @Size(max = 255)
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    @ColumnDefault("false")
    @Column(name = "is_primary")
    public Boolean getIsPrimary() {
        return isPrimary;
    }

    @Column(name = "notes", length = Integer.MAX_VALUE)
    public String getNotes() {
        return notes;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    public Instant getUpdatedAt() {
        return updatedAt;
    }

}
