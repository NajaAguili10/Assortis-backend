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
public class PartnershipSectorId implements Serializable {
    private static final long serialVersionUID = -3186044393850198815L;
    private Long partnershipId;

    private Long sectorId;

    @NotNull
    @Column(name = "partnership_id", nullable = false)
    public Long getPartnershipId() {
        return partnershipId;
    }

    @NotNull
    @Column(name = "sector_id", nullable = false)
    public Long getSectorId() {
        return sectorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        PartnershipSectorId entity = (PartnershipSectorId) o;
        return Objects.equals(this.partnershipId, entity.partnershipId) &&
                Objects.equals(this.sectorId, entity.sectorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(partnershipId, sectorId);
    }

}
