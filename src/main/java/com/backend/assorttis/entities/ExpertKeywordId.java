package com.backend.assorttis.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Embeddable
public class ExpertKeywordId implements Serializable {
    private static final long serialVersionUID = -4435912803323387453L;
    private Long expertId;

    private String keyword;

    @NotNull
    @Column(name = "expert_id", nullable = false)
    public Long getExpertId() {
        return expertId;
    }

    @Size(max = 100)
    @NotNull
    @Column(name = "keyword", nullable = false, length = 100)
    public String getKeyword() {
        return keyword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        ExpertKeywordId entity = (ExpertKeywordId) o;
        return Objects.equals(this.expertId, entity.expertId) &&
                Objects.equals(this.keyword, entity.keyword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expertId, keyword);
    }

}
