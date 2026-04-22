package com.backend.assorttis.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.ColumnDefault;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "languages", schema = "public")
public class Language {
    private String code;

    private String name;

    private String nativeName;

    private Boolean isActive;

    @Id
    @Size(max = 10)
    @Column(name = "code", nullable = false, length = 10)
    public String getCode() {
        return code;
    }

    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    public String getName() {
        return name;
    }

    @Size(max = 100)
    @Column(name = "native_name", length = 100)
    public String getNativeName() {
        return nativeName;
    }

    @ColumnDefault("true")
    @Column(name = "is_active")
    public Boolean getIsActive() {
        return isActive;
    }

}
