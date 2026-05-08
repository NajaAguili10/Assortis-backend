package com.backend.assorttis.dto.tender;

// TenderKPIsDTO.java

import lombok.Data;

@Data
public class TenderKPIsDTO {
    private Long totalTenders;
    private Long activeTenders;
    private Long closedTenders;
    private Long awardedTenders;
    private MoneyDTO averageBudget;
    private Double averageProposalsPerTender;
    private Double successRate;
    private Long mySubmissions;
    private Long myPendingSubmissions;
    private Long myInvitations;
    private MoneyDTO pipelineValue;
}
