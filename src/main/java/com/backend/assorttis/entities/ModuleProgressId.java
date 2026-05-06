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
public class ModuleProgressId implements Serializable {
    private static final long serialVersionUID = 6847960865402005419L;
    private Long enrollmentId;

    private Long moduleId;

    @NotNull
    @Column(name = "enrollment_id", nullable = false)
    public Long getEnrollmentId() {
        return enrollmentId;
    }

    @NotNull
    @Column(name = "module_id", nullable = false)
    public Long getModuleId() {
        return moduleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        ModuleProgressId entity = (ModuleProgressId) o;
        return Objects.equals(this.enrollmentId, entity.enrollmentId) &&
                Objects.equals(this.moduleId, entity.moduleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enrollmentId, moduleId);
    }

}
