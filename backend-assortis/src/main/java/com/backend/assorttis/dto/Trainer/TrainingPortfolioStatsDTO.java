package com.backend.assorttis.dto.training;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingPortfolioStatsDTO {
    private Long completedTrainings;
    private Long certifications;
    private Long achievements;
}
