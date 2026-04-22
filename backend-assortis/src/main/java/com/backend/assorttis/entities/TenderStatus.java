package com.backend.assorttis.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "tender_status", schema = "public")
public class TenderStatus {
    private String code;

    private String label;

    @Id
    @Size(max = 50)
    @Column(name = "code", nullable = false, length = 50)
    public String getCode() {
        return code;
    }

    @Size(max = 100)
    @Column(name = "label", length = 100)
    public String getLabel() {
        return label;
    }

}
