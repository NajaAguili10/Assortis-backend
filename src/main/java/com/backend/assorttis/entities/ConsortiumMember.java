package com.backend.assorttis.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "consortium_members", schema = "public")
public class ConsortiumMember {
    private ConsortiumMemberId id;

    private Consortium consortium;

    private Organization organization;

    private String role;

    @EmbeddedId
    public ConsortiumMemberId getId() {
        return id;
    }

    @MapsId("consortiumId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "consortium_id", nullable = false)
    public Consortium getConsortium() {
        return consortium;
    }

    @MapsId("organizationId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "organization_id", nullable = false)
    public Organization getOrganization() {
        return organization;
    }

    @Size(max = 50)
    @Column(name = "role", length = 50)
    public String getRole() {
        return role;
    }

}
