package com.backend.assorttis.service;

import com.backend.assorttis.dto.dashboard.DashboardStatsDTO;

/**
 * Service interface for dashboard-related operations.
 */
public interface DashboardService {
    /**
     * Get dashboard statistics for a specific user.
     *
     * @param userId the ID of the user
     * @return the dashboard statistics
     */
    DashboardStatsDTO getDashboardStats(Long userId);
}
