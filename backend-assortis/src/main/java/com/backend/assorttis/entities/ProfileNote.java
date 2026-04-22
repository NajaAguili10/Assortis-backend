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

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "profile_notes", schema = "public", indexes = {
        @Index(name = "idx_profile_notes_owner", columnList = "owner_type, owner_id")
})
public class ProfileNote {
    private Long id;

    private String ownerType;

    private Long ownerId;

    private String note;

    private User createdBy;

    private Instant createdAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Size(max = 50)
    @NotNull
    @Column(name = "owner_type", nullable = false, length = 50)
    public String getOwnerType() {
        return ownerType;
    }

    @NotNull
    @Column(name = "owner_id", nullable = false)
    public Long getOwnerId() {
        return ownerId;
    }

    @NotNull
    @Column(name = "note", nullable = false, length = Integer.MAX_VALUE)
    public String getNote() {
        return note;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "created_by")
    public User getCreatedBy() {
        return createdBy;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

}
