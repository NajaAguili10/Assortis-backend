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
    public class ExpertSubscriptionSectorId implements Serializable {
        private static final long serialVersionUID = -8193633457801637265L;
        @NotNull
        @Column(name = "expert_id", nullable = false)
        private Long expertId;

        @NotNull
        @Column(name = "sector_id", nullable = false)
        private Long sectorId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
            com.backend.assorttis.entities.ExpertSubscriptionSectorId entity = (com.backend.assorttis.entities.ExpertSubscriptionSectorId) o;
            return Objects.equals(this.expertId, entity.expertId) &&
                    Objects.equals(this.sectorId, entity.sectorId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(expertId, sectorId);
        }

    }


