package com.backend.assorttis.entities;

import jakarta.persistence.*;
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

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "cv_analyses", schema = "public")
public class CvAnalysis {
    private Long id;

    private Cv cv;

    private Instant analysisDate;

    private Map<String, Object> completenessScores;

    private String recommendations;

    private BigDecimal overallScore;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "cv_id")
    public Cv getCv() {
        return cv;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "analysis_date")
    public Instant getAnalysisDate() {
        return analysisDate;
    }

    @Column(name = "completeness_scores")
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getCompletenessScores() {
        return completenessScores;
    }

    @Column(name = "recommendations", length = Integer.MAX_VALUE)
    public String getRecommendations() {
        return recommendations;
    }

    @Column(name = "overall_score", precision = 5, scale = 2)
    public BigDecimal getOverallScore() {
        return overallScore;
    }

}
