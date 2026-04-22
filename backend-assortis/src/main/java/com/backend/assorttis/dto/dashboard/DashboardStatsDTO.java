package com.backend.assorttis.dto.dashboard;

import lombok.Builder;
import lombok.Data;

/**
 * DTO for the Mon Espace Dashboard statistics.
 */
@Data
@Builder
public class DashboardStatsDTO {
    private GlobalStatsDTO global;
    private ProjectOffersStatsDTO projectOffers;
    private InternalOffersStatsDTO internalOffers;
    private MyOffersStatsDTO myOffers;
    private InvitationStatsDTO invitations;

    @Data
    @Builder
    public static class GlobalStatsDTO {
        private long totalOffers;
        private long activeOffers;
        private long totalApplications;
        private long closingSoon;
    }

    @Data
    @Builder
    public static class ProjectOffersStatsDTO {
        private long active;
        private long closingSoon;
    }

    @Data
    @Builder
    public static class InternalOffersStatsDTO {
        private long active;
        private long departments;
    }

    @Data
    @Builder
    public static class MyOffersStatsDTO {
        private long published;
        private long applications;
    }

    @Data
    @Builder
    public static class InvitationStatsDTO {
        private long received;
        private long pending;
        private long sent;
    }
}
