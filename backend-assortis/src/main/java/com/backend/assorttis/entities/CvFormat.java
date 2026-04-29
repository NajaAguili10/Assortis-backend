package com.backend.assorttis.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "cv_format", schema = "public")
public class CvFormat {

    @Id
    @Column(name = "code", nullable = false, length = 20)
    private String code;

    @Column(name = "label", nullable = false, length = 50)
    private String label;

    @Column(name = "extension", length = 10)
    private String extension;

    @Column(name = "mime_type", length = 50)
    private String mimeType;

    @ColumnDefault("true")
    @Column(name = "is_active")
    private Boolean isActive = true;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;
}
