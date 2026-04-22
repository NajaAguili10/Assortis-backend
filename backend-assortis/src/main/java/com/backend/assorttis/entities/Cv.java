package com.backend.assorttis.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "cvs", schema = "public")
public class Cv {
    private Long id;

    private Expert expert;

    private Language language;

    private Integer version;

    private String content;

    private String fileUrl;

    private Map<String, Object> parsedData;

    private Map<String, Object> extractedContacts;

    private Boolean validated;

    private User validatedBy;

    private Instant validatedAt;

    private Instant createdAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "expert_id")
    public Expert getExpert() {
        return expert;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "language")
    public Language getLanguage() {
        return language;
    }

    @Column(name = "version")
    public Integer getVersion() {
        return version;
    }

    @Column(name = "content", length = Integer.MAX_VALUE)
    public String getContent() {
        return content;
    }

    @Column(name = "file_url", length = Integer.MAX_VALUE)
    public String getFileUrl() {
        return fileUrl;
    }

    @Column(name = "parsed_data")
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getParsedData() {
        return parsedData;
    }

    @Column(name = "extracted_contacts")
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getExtractedContacts() {
        return extractedContacts;
    }

    @ColumnDefault("false")
    @Column(name = "validated")
    public Boolean getValidated() {
        return validated;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "validated_by")
    public User getValidatedBy() {
        return validatedBy;
    }

    @Column(name = "validated_at")
    public Instant getValidatedAt() {
        return validatedAt;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

}
