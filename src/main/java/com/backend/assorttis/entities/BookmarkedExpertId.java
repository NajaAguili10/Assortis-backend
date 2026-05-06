package com.backend.assorttis.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class BookmarkedExpertId implements Serializable {
    private static final long serialVersionUID = -6363284006363442377L;
    @NotNull
    @Column(name = "organization_id", nullable = false)
    private Long organizationId;

    @NotNull
    @Column(name = "expert_id", nullable = false)
    private Long expertId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BookmarkedExpertId entity = (BookmarkedExpertId) o;
        return Objects.equals(this.organizationId, entity.organizationId) &&
                Objects.equals(this.expertId, entity.expertId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizationId, expertId);
    }

}
