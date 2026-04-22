package com.backend.assorttis.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "page_banners", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "page_banners_page_id_key", columnNames = { "page_id" })
})
public class PageBanner {
    private Long id;

    private Page page;

    private Map<String, Object> title;

    private Map<String, Object> subtitle;

    private Instant createdAt;

    private Instant updatedAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "page_id", nullable = false)
    public Page getPage() {
        return page;
    }

    @NotNull
    @Column(name = "title", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getTitle() {
        return title;
    }

    @Column(name = "subtitle")
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getSubtitle() {
        return subtitle;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    public Instant getUpdatedAt() {
        return updatedAt;
    }

}
