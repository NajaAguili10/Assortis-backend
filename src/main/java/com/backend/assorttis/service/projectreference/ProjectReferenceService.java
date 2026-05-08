package com.backend.assorttis.service.projectreference;

import com.backend.assorttis.dto.projectreference.ProjectReferenceDto;
import com.backend.assorttis.dto.projectreference.ProjectReferenceTemplate;
import com.backend.assorttis.dto.projectreference.TemplateOptionDto;
import com.backend.assorttis.entities.Donor;
import com.backend.assorttis.entities.Project;
import com.backend.assorttis.entities.ProjectTeamAssignment;
import com.backend.assorttis.entities.User;
import com.backend.assorttis.enums.ProjectNotFoundException;
import com.backend.assorttis.repository.ProjectOrganizationRepository;
import com.backend.assorttis.repository.ProjectRepository;
import com.backend.assorttis.repository.ProjectTeamAssignmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectReferenceService {

    private final ProjectRepository projectRepository;
    private final ProjectOrganizationRepository projectOrganizationRepository;
    private final ProjectTeamAssignmentRepository projectTeamAssignmentRepository;
    private final DonorTemplateRegistry donorTemplateRegistry;
    private final DocxGenerationService docxGenerationService;

    @Value("${project-references.missing-value:N/A}")
    private String missingValue;

    public List<TemplateOptionDto> listTemplates() {
        return donorTemplateRegistry.listTemplates();
    }

    @Transactional(readOnly = true)
    public GeneratedProjectReference generate(Long projectId, String format) {
        log.info("Starting project reference generation projectId={} format={}", projectId, format);
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found"));
        ProjectReferenceTemplate template = donorTemplateRegistry.getTemplate(format);
        ProjectReferenceDto dto = buildDto(project);
        byte[] content = docxGenerationService.generate(template, dto.getVariables());
        log.info("Generated project reference projectId={} format={} bytes={}", projectId, template.getFormat(), content.length);

        return new GeneratedProjectReference(
                content,
                buildFileName(project.getTitle(), template.getFormat().name())
        );
    }

    private ProjectReferenceDto buildDto(Project project) {
        String donorName = donorName(project.getDonor());
        String leadOrganization = safe(projectOrganizationRepository.findLeadOrganizationNameByProjectId(project.getId()));
        String partners = join(projectOrganizationRepository.findPartnerOrganizationNamesByProjectId(project.getId()));
        String experts = join(expertProfiles(project.getId()));
        String activities = activities(project);
        String contractValue = contractValue(project.getCurrency(), project.getBudget());
        String typeAndScope = joinParts(List.of(project.getType(), project.getScope()));
        String fundingSource = joinParts(List.of(donorName, project.getFundingType()));

        Map<String, String> variables = new LinkedHashMap<>();
        variables.put("projectTitle", safe(project.getTitle()));
        variables.put("clientName", firstPresent(donorName, project.getSource()));
        variables.put("country", project.getCountry() != null ? safe(project.getCountry().getName()) : missingValue);
        variables.put("fundingSource", safe(fundingSource));
        variables.put("donor", safe(donorName));
        variables.put("contractValue", safe(contractValue));
        variables.put("startDate", project.getStartDate() != null ? project.getStartDate().format(DateTimeFormatter.ISO_DATE) : missingValue);
        variables.put("endDate", project.getEndDate() != null ? project.getEndDate().format(DateTimeFormatter.ISO_DATE) : missingValue);
        variables.put("legalEntity", leadOrganization);
        variables.put("organization", leadOrganization);
        variables.put("consortiumMembers", partners);
        variables.put("projectDescription", safe(project.getDescription()));
        variables.put("typeAndScopeOfServices", safe(typeAndScope));
        variables.put("expertsProvided", experts);
        variables.put("activitiesCarriedOut", activities);

        return ProjectReferenceDto.builder()
                .projectId(project.getId())
                .projectTitle(variables.get("projectTitle"))
                .clientName(variables.get("clientName"))
                .country(variables.get("country"))
                .fundingSource(variables.get("fundingSource"))
                .donor(variables.get("donor"))
                .contractValue(variables.get("contractValue"))
                .startDate(variables.get("startDate"))
                .endDate(variables.get("endDate"))
                .legalEntity(variables.get("legalEntity"))
                .organization(variables.get("organization"))
                .consortiumMembers(variables.get("consortiumMembers"))
                .projectDescription(variables.get("projectDescription"))
                .typeAndScopeOfServices(variables.get("typeAndScopeOfServices"))
                .expertsProvided(variables.get("expertsProvided"))
                .activitiesCarriedOut(variables.get("activitiesCarriedOut"))
                .variables(variables)
                .build();
    }

    private String donorName(Donor donor) {
        if (donor == null) {
            return missingValue;
        }
        return firstPresent(donor.getShortName(), donor.getName());
    }

    private String activities(Project project) {
        String deliverables = project.getDeliverables() == null ? "" : join(project.getDeliverables().keySet().stream().toList());
        String objectives = project.getObjectives() == null ? "" : join(project.getObjectives().keySet().stream().toList());
        return firstPresent(deliverables, objectives, project.getScope(), project.getDescription());
    }

    private String contractValue(String currency, BigDecimal budget) {
        if (budget == null) {
            return missingValue;
        }
        return joinParts(List.of(currency, budget.toPlainString()));
    }

    private List<String> expertProfiles(Long projectId) {
        return projectTeamAssignmentRepository.findByProjectId(projectId).stream()
                .map(this::expertProfile)
                .toList();
    }

    private String expertProfile(ProjectTeamAssignment assignment) {
        String role = firstPresent(assignment.getRole(), "Expert");
        User user = assignment.getUser();
        if (user == null) {
            return role;
        }
        String fullName = joinParts(List.of(user.getFirstName(), user.getLastName()));
        return joinParts(List.of(role, firstPresent(fullName, user.getEmail())));
    }

    private String join(List<String> values) {
        String joined = values == null ? "" : values.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(value -> !value.isBlank())
                .collect(Collectors.joining("\n"));
        return safe(joined);
    }

    private String joinParts(List<String> values) {
        return values.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(value -> !value.isBlank())
                .collect(Collectors.joining(" - "));
    }

    private String firstPresent(String... values) {
        for (String value : values) {
            if (value != null && !value.trim().isBlank() && !missingValue.equals(value)) {
                return value.trim();
            }
        }
        return missingValue;
    }

    private String safe(String value) {
        return value == null || value.trim().isBlank() ? missingValue : value.trim();
    }

    private String buildFileName(String projectTitle, String format) {
        String slug = safe(projectTitle)
                .toLowerCase()
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-|-$)", "");
        return "project-reference-" + slug + "-" + format + ".docx";
    }

    @lombok.Value
    public static class GeneratedProjectReference {
        byte[] content;
        String fileName;
    }
}
