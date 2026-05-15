package com.backend.assorttis.mappers;

import com.backend.assorttis.dto.project.*;
import com.backend.assorttis.entities.*;
import com.backend.assorttis.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProjectMapper {

    private final ProjectSubsectorRepository subsectorRepository;
    private final ProjectExpensRepository expensRepository;
    private final ProjectTaskRepository taskRepository;
    private final ProjectOrganizationRepository projectOrganizationRepository;
    private final ProjectTeamAssignmentRepository teamAssignmentRepository;
    private final ProjectMilestoneRepository milestoneRepository;

    public ProjectListDTO toProjectListDTO(Project project) {
        ProjectListDTO dto = new ProjectListDTO();
        dto.setId(project.getId());
        dto.setReferenceCode(project.getReferenceCode());
        dto.setCode(project.getReferenceCode());
        dto.setTitle(project.getTitle());
        dto.setName(project.getTitle());
        dto.setDescription(project.getDescription());
        if (project.getStatus() != null) {
            dto.setStatus(project.getStatus().name());
        }
        if (project.getPriority() != null) {
            dto.setPriority(project.getPriority().name());
        }
        if (project.getType() != null) {
            dto.setType(project.getType().name());
        }

        // Location
        if (project.getCountry() != null) {
            dto.setCountry(project.getCountry().getName());
        }

        if (project.getCity() != null) {
            dto.setCity(project.getCity().getName());
        }

        dto.setRegion(project.getRegion());

        // Sector
        if (project.getMainSector() != null) {
            dto.setSector(project.getMainSector().getCode());
        }

        List<String> subsectorNames = subsectorRepository.findSubsectorNamesByProjectId(project.getId());
        dto.setSubsectors(subsectorNames);

        // Budget
        ProjectListDTO.ProjectBudgetDTO budgetDTO = new ProjectListDTO.ProjectBudgetDTO();
        BigDecimal totalBudget = project.getBudget() != null ? project.getBudget() : BigDecimal.ZERO;
        budgetDTO.setTotal(totalBudget);
        BigDecimal spent = expensRepository.sumSpentBudgetByProjectId(project.getId());
        spent = (spent != null) ? spent : BigDecimal.ZERO;
        budgetDTO.setSpent(spent);
        budgetDTO.setRemaining(totalBudget.subtract(spent));
        budgetDTO.setCurrency(project.getCurrency());
        dto.setBudget(budgetDTO);
        dto.setCurrency(project.getCurrency());

        // Organization
        if (project.getDonor() != null) {
            ProjectListDTO.DonorDTO donorDTO = new ProjectListDTO.DonorDTO();
            donorDTO.setId(project.getDonor().getId());
            donorDTO.setName(project.getDonor().getName());
            donorDTO.setShortName(project.getDonor().getShortName());
            donorDTO.setType(project.getDonor().getType());
            dto.setDonor(donorDTO);
        }
        dto.setFundingType(project.getFundingType());

        String leadOrg = projectOrganizationRepository.findLeadOrganizationNameByProjectId(project.getId());
        dto.setLeadOrganization(leadOrg);
        List<String> partners = projectOrganizationRepository.findPartnerOrganizationNamesByProjectId(project.getId());
        dto.setPartners(partners);
        Integer teamSize = teamAssignmentRepository.countTeamMembersByProjectId(project.getId());
        dto.setTeamSize(teamSize != null ? teamSize : 0);

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

        if (project.getStartDate() != null) {
            dto.setStartDate(project.getStartDate().toString());
        }
        if (project.getEndDate() != null) {
            dto.setEndDate(project.getEndDate().toString());
        }

        // Tasks stats
        dto.setTotalTasks(totalTasks != null ? totalTasks : 0);
        dto.setTasksCompleted(completedTasks != null ? completedTasks : 0);

        // Audit
        if (project.getUpdatedAt() != null) {
            dto.setUpdatedAt(project.getUpdatedAt().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        }

        return dto;
    }

    public ProjectDetailDTO toProjectDetailDTO(Project project) {
        ProjectDetailDTO dto = new ProjectDetailDTO();
        ProjectListDTO listDto = toProjectListDTO(project);
        
        dto.setId(listDto.getId());
        dto.setReferenceCode(listDto.getReferenceCode());
        dto.setCode(listDto.getCode());
        dto.setTitle(listDto.getTitle());
        dto.setName(listDto.getName());
        dto.setDescription(listDto.getDescription());
        dto.setStatus(listDto.getStatus());
        dto.setPriority(listDto.getPriority());
        dto.setType(listDto.getType());
        dto.setCountry(listDto.getCountry());
        dto.setCity(listDto.getCity());
        dto.setRegion(listDto.getRegion());
        dto.setSector(listDto.getSector());
        dto.setSubsectors(listDto.getSubsectors());
        dto.setBudget(listDto.getBudget());
        dto.setCurrency(listDto.getCurrency());
        dto.setDonor(listDto.getDonor());
        dto.setFundingType(listDto.getFundingType());
        dto.setLeadOrganization(listDto.getLeadOrganization());
        dto.setPartners(listDto.getPartners());
        dto.setTeamSize(listDto.getTeamSize());
        dto.setTimeline(listDto.getTimeline());
        dto.setStartDate(listDto.getStartDate());
        dto.setEndDate(listDto.getEndDate());
        dto.setTasksCompleted(listDto.getTasksCompleted());
        dto.setTotalTasks(listDto.getTotalTasks());
        dto.setUpdatedAt(listDto.getUpdatedAt());

        if (project.getObjectives() != null) {
            dto.setObjectives(new ArrayList<>(project.getObjectives().keySet()));
        }
        if (project.getDeliverables() != null) {
            dto.setDeliverables(new ArrayList<>(project.getDeliverables().keySet()));
        }

        // Map Team Members
        List<ProjectTeamAssignment> teamAssignments = teamAssignmentRepository.findByProjectId(project.getId());
        dto.setTeam(mapTeamMembers(teamAssignments));

        // Map Milestones
        List<ProjectMilestone> milestones = milestoneRepository.findByProjectId(project.getId());
        dto.setMilestones(mapMilestones(milestones));

        return dto;
    }

    private List<ProjectDetailDTO.ProjectTeamMemberDTO> mapTeamMembers(List<ProjectTeamAssignment> assignments) {
        if (assignments == null) return new ArrayList<>();
        return assignments.stream().map(a -> {
            ProjectDetailDTO.ProjectTeamMemberDTO m = new ProjectDetailDTO.ProjectTeamMemberDTO();
            m.setId(a.getId());
            if (a.getUser() != null) {
                m.setName(a.getUser().getFirstName() + " " + a.getUser().getLastName());
                // In a real app, you'd get avatar URL from user profile
                m.setAvatar(null);
            }
            m.setRole(a.getRole());
            return m;
        }).collect(java.util.stream.Collectors.toList());
    }

    private List<ProjectDetailDTO.ProjectMilestoneDTO> mapMilestones(List<ProjectMilestone> milestones) {
        if (milestones == null) return new ArrayList<>();
        return milestones.stream().map(m -> {
            ProjectDetailDTO.ProjectMilestoneDTO dto = new ProjectDetailDTO.ProjectMilestoneDTO();
            dto.setId(m.getId());
            dto.setTitle(m.getTitle());
            dto.setDescription(m.getDescription());
            dto.setDueDate(m.getDueDate());
            dto.setStatus(m.getStatus());
            if (m.getCompletedAt() != null) {
                dto.setCompletedDate(m.getCompletedAt().atZone(ZoneId.systemDefault()).toLocalDate());
            }
            return dto;
        }).collect(java.util.stream.Collectors.toList());
    }

    public ProjectSavedSearchDTO toProjectSavedSearchDTO(ProjectSavedSearch entity) {
        if (entity == null) return null;
        ProjectSavedSearchDTO dto = new ProjectSavedSearchDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPayload(entity.getPayload());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    public List<ProjectSavedSearchDTO> toProjectSavedSearchDTOList(List<ProjectSavedSearch> entities) {
        if (entities == null) return new ArrayList<>();
        return entities.stream().map(this::toProjectSavedSearchDTO).collect(java.util.stream.Collectors.toList());
    }
}
