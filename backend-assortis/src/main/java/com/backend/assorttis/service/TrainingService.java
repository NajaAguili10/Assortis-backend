package com.backend.assorttis.service;

import com.backend.assorttis.dto.training.TrainingPortfolioStatsDTO;

public interface TrainingService {
    TrainingPortfolioStatsDTO getTrainingPortfolioStats(Long organizationId);
}
