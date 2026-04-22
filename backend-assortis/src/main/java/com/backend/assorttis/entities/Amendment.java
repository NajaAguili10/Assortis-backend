package com.backend.assorttis.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "amendments", schema = "public", indexes = {
        @Index(name = "idx_amendments_tor", columnList = "tor_id")
})
public class Amendment {
    private Long id;

    private TenderTor tor;

    private Integer amendmentNumber;

    private String title;

    private String description;

    private LocalDate amendmentDate;

    private Instant createdAt;

    private User createdBy;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "tor_id", nullable = false)
    public TenderTor getTor() {
        return tor;
    }

    @NotNull
    @Column(name = "amendment_number", nullable = false)
    public Integer getAmendmentNumber() {
        return amendmentNumber;
    }

    @Size(max = 255)
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    @Column(name = "description", length = Integer.MAX_VALUE)
    public String getDescription() {
        return description;
    }

    @NotNull
    @Column(name = "amendment_date", nullable = false)
    public LocalDate getAmendmentDate() {
        return amendmentDate;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "created_by")
    public User getCreatedBy() {
        return createdBy;
    }

}
