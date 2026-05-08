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

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "countries", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "countries_code_key", columnNames = { "code" })
})
public class Country {
    private Long id;

    private String code;

    private String iso2;

    private String iso3;

    private String name;

    private String regionWorld;

    private String currencyCode;

    private Boolean isActive;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Size(max = 10)
    @NotNull
    @Column(name = "code", nullable = false, length = 10)
    public String getCode() {
        return code;
    }

    @Size(max = 2)
    @Column(name = "iso2", length = 2)
    public String getIso2() {
        return iso2;
    }

    @Size(max = 3)
    @Column(name = "iso3", length = 3)
    public String getIso3() {
        return iso3;
    }

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    @Size(max = 100)
    @Column(name = "region_world", length = 100)
    public String getRegionWorld() {
        return regionWorld;
    }

    @Size(max = 10)
    @Column(name = "currency_code", length = 10)
    public String getCurrencyCode() {
        return currencyCode;
    }

    @ColumnDefault("true")
    @Column(name = "is_active")
    public Boolean getIsActive() {
        return isActive;
    }

}
