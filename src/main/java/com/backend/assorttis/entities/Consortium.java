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
@Table(name = "consortiums", schema = "public")
public class Consortium {
    private Long id;

    private Tender tender;

    private String name;

    private Organization leadOrganization;

    private String status;

    private Instant createdAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "tender_id")
    public Tender getTender() {
        return tender;
    }

    @Size(max = 255)
    @Column(name = "name")
    public String getName() {
        return name;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "lead_organization_id")
    public Organization getLeadOrganization() {
        return leadOrganization;
    }

    @Size(max = 50)
    @Column(name = "status", length = 50)
    public String getStatus() {
        return status;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

}
