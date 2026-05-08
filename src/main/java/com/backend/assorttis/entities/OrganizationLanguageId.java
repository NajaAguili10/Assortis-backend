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
public class OrganizationLanguageId implements Serializable {
    private static final long serialVersionUID = -2321541407018509699L;
    private Long organizationId;

    private String languageCode;

    @NotNull
    @Column(name = "organization_id", nullable = false)
    public Long getOrganizationId() {
        return organizationId;
    }

    @Size(max = 10)
    @NotNull
    @Column(name = "language_code", nullable = false, length = 10)
    public String getLanguageCode() {
        return languageCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        OrganizationLanguageId entity = (OrganizationLanguageId) o;
        return Objects.equals(this.organizationId, entity.organizationId) &&
                Objects.equals(this.languageCode, entity.languageCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizationId, languageCode);
    }

}
