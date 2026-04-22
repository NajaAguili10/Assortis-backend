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
@Table(name = "deliverables", schema = "public")
public class Deliverable {
    private Long id;

    private Contract contract;

    private String title;

    private String fileUrl;

    private String status;

    private Instant submittedAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "contract_id")
    public Contract getContract() {
        return contract;
    }

    @Size(max = 255)
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    @Column(name = "file_url", length = Integer.MAX_VALUE)
    public String getFileUrl() {
        return fileUrl;
    }

    @Size(max = 50)
    @Column(name = "status", length = 50)
    public String getStatus() {
        return status;
    }

    @Column(name = "submitted_at")
    public Instant getSubmittedAt() {
        return submittedAt;
    }

}
