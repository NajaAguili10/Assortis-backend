package com.backend.assorttis.dto.project;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProjectDetailDTO extends ProjectListDTO {
    private List<String> objectives;
    private List<String> deliverables;
    private List<String> risks;
    private List<ProjectTeamMemberDTO> team;
    private List<ProjectMilestoneDTO> milestones;
    private Integer beneficiaries;
    private String impactArea;
    private String fundingSource;
    private List<String> relatedProjects;
    private List<DocumentDTO> documents;

    @Data
    public static class ProjectTeamMemberDTO {
        private Long id;
        private String name;
        private String role;
        private String organization;
        private String avatar;
    }

    @Data
    public static class ProjectMilestoneDTO {
        private Long id;
        private String title;
        private String description;
        private LocalDate dueDate;
        private LocalDate completedDate;
        private String status; // PENDING, IN_PROGRESS, COMPLETED, DELAYED
    }

    @Data
    public static class DocumentDTO {
        private Long id;
        private String name;
        private String type;
        private String url;
        private LocalDate uploadedDate;
    }
}
