package com.backend.assorttis.entities;

import jakarta.persistence.*;
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
@Table(name = "tender_history", schema = "public", indexes = {
        @Index(name = "idx_history_tender", columnList = "tender_id")
})
public class TenderHistory {
    private Long id;

    private Tender tender;

    private Instant changedAt;

    private Long changedBy;

    private String fieldName;

    private String oldValue;

    private String newValue;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "tender_id")
    public Tender getTender() {
        return tender;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "changed_at")
    public Instant getChangedAt() {
        return changedAt;
    }

    @Column(name = "changed_by")
    public Long getChangedBy() {
        return changedBy;
    }

    @Size(max = 100)
    @Column(name = "field_name", length = 100)
    public String getFieldName() {
        return fieldName;
    }

    @Column(name = "old_value", length = Integer.MAX_VALUE)
    public String getOldValue() {
        return oldValue;
    }

    @Column(name = "new_value", length = Integer.MAX_VALUE)
    public String getNewValue() {
        return newValue;
    }

}
