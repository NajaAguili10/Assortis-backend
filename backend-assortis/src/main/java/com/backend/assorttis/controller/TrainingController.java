package com.backend.assorttis.controller;

import com.backend.assorttis.dto.training.TrainingPortfolioStatsDTO;
import com.backend.assorttis.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/training")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingService trainingService;

    @GetMapping("/portfolio/stats")
    public ResponseEntity<TrainingPortfolioStatsDTO> getTrainingPortfolioStats(@RequestParam Long organizationId) {
        return ResponseEntity.ok(trainingService.getTrainingPortfolioStats(organizationId));
    }
}
