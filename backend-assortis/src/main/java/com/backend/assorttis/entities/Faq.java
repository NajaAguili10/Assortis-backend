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
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "faqs", schema = "public", indexes = {
        @Index(name = "idx_faqs_category", columnList = "category_id")
})
public class Faq {
    private Long id;

    private Long categoryId;

    private String question;

    private String answer;

    private Integer sortOrder;

    private Boolean isActive;

    private Instant createdAt;

    private Instant updatedAt;

    private Map<String, Object> questionJsonb;

    private Map<String, Object> answerJsonb;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "category_id")
    public Long getCategoryId() {
        return categoryId;
    }

    @NotNull
    @Column(name = "question", nullable = false, length = Integer.MAX_VALUE)
    public String getQuestion() {
        return question;
    }

    @NotNull
    @Column(name = "answer", nullable = false, length = Integer.MAX_VALUE)
    public String getAnswer() {
        return answer;
    }

    @ColumnDefault("0")
    @Column(name = "sort_order")
    public Integer getSortOrder() {
        return sortOrder;
    }

    @ColumnDefault("true")
    @Column(name = "is_active")
    public Boolean getIsActive() {
        return isActive;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

    @Column(name = "updated_at")
    public Instant getUpdatedAt() {
        return updatedAt;
    }

    @Column(name = "question_jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getQuestionJsonb() {
        return questionJsonb;
    }

    @Column(name = "answer_jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getAnswerJsonb() {
        return answerJsonb;
    }

}
