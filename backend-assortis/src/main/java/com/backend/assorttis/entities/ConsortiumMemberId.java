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
public class ConsortiumMemberId implements Serializable {
    private static final long serialVersionUID = -4059818862599294594L;
    private Long consortiumId;

    private Long organizationId;

    @NotNull
    @Column(name = "consortium_id", nullable = false)
    public Long getConsortiumId() {
        return consortiumId;
    }

    @NotNull
    @Column(name = "organization_id", nullable = false)
    public Long getOrganizationId() {
        return organizationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        ConsortiumMemberId entity = (ConsortiumMemberId) o;
        return Objects.equals(this.organizationId, entity.organizationId) &&
                Objects.equals(this.consortiumId, entity.consortiumId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizationId, consortiumId);
    }

}
