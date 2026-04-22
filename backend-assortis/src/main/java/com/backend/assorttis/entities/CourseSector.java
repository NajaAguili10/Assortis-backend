package com.backend.assorttis.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * Entité représentant la table de liaison course_sectors (many-to-many entre Cours et Sector).
 * Contient une colonne supplémentaire created_at.
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "course_sectors", schema = "public")
public class CourseSector {

    @EmbeddedId
    private CourseSectorId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @MapsId("courseId") // correspond au champ courseId de l'ID
    @JoinColumn(name = "course_id", nullable = false)
    private Cours course;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @MapsId("sectorId") // correspond au champ sectorId de l'ID
    @JoinColumn(name = "sector_id", nullable = false)
    private Sector sector;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", insertable = false, updatable = false)
    private Instant createdAt;

    // Constructeur pratique
    public CourseSector(Cours course, Sector sector) {
        this.course = course;
        this.sector = sector;
        this.id = new CourseSectorId(course.getId(), sector.getId());
    }

    /**
     * Clé composite embarquée.
     */
    @Embeddable
    public static class CourseSectorId implements Serializable {

        @Column(name = "course_id", nullable = false)
        private Long courseId;

        @Column(name = "sector_id", nullable = false)
        private Long sectorId;

        public CourseSectorId() {}

        public CourseSectorId(Long courseId, Long sectorId) {
            this.courseId = courseId;
            this.sectorId = sectorId;
        }

        // Getters, setters, equals, hashCode
        public Long getCourseId() { return courseId; }
        public void setCourseId(Long courseId) { this.courseId = courseId; }
        public Long getSectorId() { return sectorId; }
        public void setSectorId(Long sectorId) { this.sectorId = sectorId; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CourseSectorId that = (CourseSectorId) o;
            return Objects.equals(courseId, that.courseId) &&
                    Objects.equals(sectorId, that.sectorId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(courseId, sectorId);
        }
    }
}
