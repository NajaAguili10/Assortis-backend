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
public class UserRoleId implements Serializable {
    private static final long serialVersionUID = 7370286435734623062L;
    private Long userId;

    private Long roleId;

    private String scopeType;

    private Long scopeId;

    @NotNull
    @Column(name = "user_id", nullable = false)
    public Long getUserId() {
        return userId;
    }

    @NotNull
    @Column(name = "role_id", nullable = false)
    public Long getRoleId() {
        return roleId;
    }

    @Size(max = 50)
    @NotNull
    @Column(name = "scope_type", nullable = false, length = 50)
    public String getScopeType() {
        return scopeType;
    }

    @NotNull
    @Column(name = "scope_id", nullable = false)
    public Long getScopeId() {
        return scopeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        UserRoleId entity = (UserRoleId) o;
        return Objects.equals(this.scopeId, entity.scopeId) &&
                Objects.equals(this.scopeType, entity.scopeType) &&
                Objects.equals(this.roleId, entity.roleId) &&
                Objects.equals(this.userId, entity.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scopeId, scopeType, roleId, userId);
    }

}
