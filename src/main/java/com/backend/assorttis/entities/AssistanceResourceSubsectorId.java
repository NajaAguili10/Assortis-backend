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
public class AssistanceResourceSubsectorId implements Serializable {
    private static final long serialVersionUID = -5373405589776631868L;
    private Long resourceId;

    private Long subsectorId;

    @NotNull
    @Column(name = "resource_id", nullable = false)
    public Long getResourceId() {
        return resourceId;
    }

    @NotNull
    @Column(name = "subsector_id", nullable = false)
    public Long getSubsectorId() {
        return subsectorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        AssistanceResourceSubsectorId entity = (AssistanceResourceSubsectorId) o;
        return Objects.equals(this.resourceId, entity.resourceId) &&
                Objects.equals(this.subsectorId, entity.subsectorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resourceId, subsectorId);
    }

}
