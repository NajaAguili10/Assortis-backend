// New mapper class
package com.backend.assorttis.mappers;

import com.backend.assorttis.dto.project.*;
import com.backend.assorttis.entities.*;
import com.backend.assorttis.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProjectMapper {

    private final ProjectSubsectorRepository subsectorRepository;
    private final ProjectExpensRepository expensRepository;
    private final ProjectTaskRepository taskRepository;
    private final ProjectOrganizationRepository projectOrganizationRepository;
    private final ProjectTeamAssignmentRepository teamAssignmentRepository;

    public ProjectListDTO toProjectListDTO(Project project) {
        ProjectListDTO dto = new ProjectListDTO();
        dto.setId(project.getId());
        dto.setCode(project.getReferenceCode());
        dto.setTitle(project.getTitle());
        dto.setDescription(project.getDescription());
        dto.setStatus(project.getStatus());
        dto.setPriority(project.getPriority());
        dto.setType(project.getType());

        // Location
        dto.setCountry(project.getCountry() != null ? project.getCountry().getName() : null);
        dto.setRegion(project.getRegion());

        // Sector
        dto.setSector(project.getMainSector() != null ? project.getMainSector().getName() : null);
        List<String> subsectorNames = subsectorRepository.findSubsectorNamesByProjectId(project.getId());
        dto.setSubsectors(subsectorNames);

        // Budget
        ProjectListDTO.ProjectBudgetDTO budgetDTO = new ProjectListDTO.ProjectBudgetDTO();
        budgetDTO.setTotal(project.getBudget());
        BigDecimal spent = expensRepository.sumSpentBudgetByProjectId(project.getId());
        budgetDTO.setSpent(spent);
        budgetDTO.setRemaining(project.getBudget().subtract(spent));
        budgetDTO.setCurrency(project.getCurrency());
        dto.setBudget(budgetDTO);

        // Timeline
        ProjectListDTO.ProjectTimelineDTO timelineDTO = new ProjectListDTO.ProjectTimelineDTO();
        timelineDTO.setStartDate(project.getStartDate());
        timelineDTO.setEndDate(project.getEndDate());
        if (project.getStartDate() != null && project.getEndDate() != null) {
            long months = ChronoUnit.MONTHS.between(project.getStartDate(), project.getEndDate());
            timelineDTO.setDuration((int) months);
        }
        Integer totalTasks = taskRepository.countTotalTasksByProjectId(project.getId());
        Integer completedTasks = taskRepository.countCompletedTasksByProjectId(project.getId());
        if (totalTasks != null && totalTasks > 0) {
            int completion = (completedTasks * 100) / totalTasks;
            timelineDTO.setCompletionPercentage(completion);
        } else {
            timelineDTO.setCompletionPercentage(0);
        }
        dto.setTimeline(timelineDTO);

        // Organization
        String leadOrg = projectOrganizationRepository.findLeadOrganizationNameByProjectId(project.getId());
        dto.setLeadOrganization(leadOrg);
        List<String> partners = projectOrganizationRepository.findPartnerOrganizationNamesByProjectId(project.getId());
        dto.setPartners(partners);
        Integer teamSize = teamAssignmentRepository.countTeamMembersByProjectId(project.getId());
        dto.setTeamSize(teamSize != null ? teamSize : 0);

        // Tasks stats
        dto.setTotalTasks(totalTasks != null ? totalTasks : 0);
        dto.setTasksCompleted(completedTasks != null ? completedTasks : 0);

        // Dates
        dto.setUpdatedDate(project.getUpdatedAt() != null ?
                project.getUpdatedAt().atZone(ZoneId.systemDefault()).toLocalDate() : null);

        return dto;
    }

    public ProjectDetailDTO toProjectDetailDTO(Project project) {
        ProjectDetailDTO dto = new ProjectDetailDTO();
        // Copy common fields from list DTO
        ProjectListDTO listDto = toProjectListDTO(project);
        // Manually copy fields (or use a mapper library like MapStruct)
        dto.setId(listDto.getId());
        dto.setCode(listDto.getCode());
        dto.setTitle(listDto.getTitle());
        dto.setDescription(listDto.getDescription());
        dto.setStatus(listDto.getStatus());
        dto.setPriority(listDto.getPriority());
        dto.setType(listDto.getType());
        dto.setCountry(listDto.getCountry());
        dto.setRegion(listDto.getRegion());
        dto.setSector(listDto.getSector());
        dto.setSubsectors(listDto.getSubsectors());
        dto.setBudget(listDto.getBudget());
        dto.setTimeline(listDto.getTimeline());
        dto.setLeadOrganization(listDto.getLeadOrganization());
        dto.setPartners(listDto.getPartners());
        dto.setTeamSize(listDto.getTeamSize());
        dto.setTotalTasks(listDto.getTotalTasks());
        dto.setTasksCompleted(listDto.getTasksCompleted());
        dto.setUpdatedDate(listDto.getUpdatedDate());

        // Additional fields for detail
        if (project.getObjectives() != null) {
            dto.setObjectives(new ArrayList<>(project.getObjectives().keySet()));
        }
        if (project.getDeliverables() != null) {
            dto.setDeliverables(new ArrayList<>(project.getDeliverables().keySet()));
        }
        // Risks, team, milestones, documents would be fetched similarly
        // For brevity, they are left empty or can be populated with additional queries

        return dto;
    }
}
