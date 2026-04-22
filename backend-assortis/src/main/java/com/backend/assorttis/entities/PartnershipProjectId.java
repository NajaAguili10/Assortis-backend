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
public class PartnershipProjectId implements Serializable {
    private static final long serialVersionUID = 5235189512678288961L;
    private Long partnershipId;

    private Long projectId;

    @NotNull
    @Column(name = "partnership_id", nullable = false)
    public Long getPartnershipId() {
        return partnershipId;
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
        PartnershipProjectId entity = (PartnershipProjectId) o;
        return Objects.equals(this.partnershipId, entity.partnershipId) &&
                Objects.equals(this.projectId, entity.projectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(partnershipId, projectId);
    }

}
