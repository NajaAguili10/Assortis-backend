package com.backend.assorttis.controller;

import com.backend.assorttis.dto.dashboard.DashboardStatsDTO;
import com.backend.assorttis.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for dashboard-related endpoints.
 */
@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DashboardController {

    private final DashboardService dashboardService;

    /**
     * Get dashboard statistics for a specific user.
     * In a production environment, this would typically use the authenticated user's ID
     * from the security context (e.g., JWT token).
     *
     * @param userId the ID of the user to fetch stats for
     * @return a response entity containing the dashboard stats
     */
    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsDTO> getDashboardStats(@RequestParam(name = "userId") Long userId) {
        DashboardStatsDTO stats = dashboardService.getDashboardStats(userId);
        return ResponseEntity.ok(stats);
    }
}
