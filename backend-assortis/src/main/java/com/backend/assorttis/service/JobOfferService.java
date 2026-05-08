package com.backend.assorttis.service;

import com.backend.assorttis.dto.joboffer.JobOfferRequestDTO;
import com.backend.assorttis.dto.joboffer.JobOfferResponseDTO;
import com.backend.assorttis.entities.JobOffer;
import com.backend.assorttis.entities.Organization;
import com.backend.assorttis.entities.Project;
import com.backend.assorttis.repository.JobApplicationRepository;
import com.backend.assorttis.repository.JobOfferRepository;
import com.backend.assorttis.repository.OrganizationRepository;
import com.backend.assorttis.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import static com.backend.assorttis.entities.enums.project.ProjectStatus.ACTIVE;

@Service
@RequiredArgsConstructor
public class JobOfferService {

    private final JobOfferRepository jobOfferRepository;
    private final OrganizationRepository organizationRepository;
    private final ProjectRepository projectRepository;
    private final JobApplicationRepository jobApplicationRepository;

    @Transactional(readOnly = true)
    public List<JobOfferResponseDTO> getAllJobOffers() {
        return jobOfferRepository.findAll().stream()
                .sorted(Comparator.comparing(JobOffer::getCreatedAt, Comparator.nullsLast(Comparator.reverseOrder())))
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public JobOfferResponseDTO getJobOfferById(Long id) {
        JobOffer jobOffer = jobOfferRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Job offer not found: " + id));
        return toResponse(jobOffer);
    }

    @Transactional
    public JobOfferResponseDTO createJobOffer(JobOfferRequestDTO request) {
        JobOffer jobOffer = new JobOffer()
                .setCreatedAt(Instant.now())
                .setVisibility(true)
                .setRemote(false);

        applyRequest(jobOffer, request);
        return toResponse(jobOfferRepository.save(jobOffer));
    }

    @Transactional
    public JobOfferResponseDTO updateJobOffer(Long id, JobOfferRequestDTO request) {
        JobOffer jobOffer = jobOfferRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Job offer not found: " + id));

        applyRequest(jobOffer, request);
        return toResponse(jobOfferRepository.save(jobOffer));
    }

    @Transactional
    public void deleteJobOffer(Long id) {
        if (!jobOfferRepository.existsById(id)) {
            throw new NoSuchElementException("Job offer not found: " + id);
        }

        jobOfferRepository.deleteById(id);
    }

    private void applyRequest(JobOffer jobOffer, JobOfferRequestDTO request) {
        jobOffer.setTitle(request.getTitle());
        jobOffer.setDescription(request.getDescription());
        jobOffer.setContractType(request.getContractType());
        jobOffer.setDeadline(request.getDeadline());
        jobOffer.setStatus(request.getStatus() != null && !request.getStatus().isBlank() ? request.getStatus() : "PUBLISHED");
        jobOffer.setOrganization(resolveOrganization(request.getOrganizationId()));
        jobOffer.setProject(resolveProject(request.getType(), request.getProjectTitle()));
    }

    private Organization resolveOrganization(Long organizationId) {
        if (organizationId != null) {
            return organizationRepository.findById(organizationId)
                    .orElseGet(this::getFallbackOrganization);
        }

        return getFallbackOrganization();
    }

    private Organization getFallbackOrganization() {
        return organizationRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No organization available for job offer"));
    }

    private Project resolveProject(String type, String projectTitle) {
        if (!"PROJECT".equalsIgnoreCase(type) || projectTitle == null || projectTitle.isBlank()) {
            return null;
        }

        return projectRepository.findAll().stream()
                .filter(project -> project.getTitle() != null && project.getTitle().equalsIgnoreCase(projectTitle))
                .findFirst()
                .orElseGet(() -> projectRepository.save(
                        new Project()
                                .setTitle(projectTitle)
                                .setStatus(ACTIVE)
                                .setUpdatedAt(Instant.now())
                ));
    }

    private JobOfferResponseDTO toResponse(JobOffer jobOffer) {
        int totalApplications = (int) jobApplicationRepository.countByJobOfferId(jobOffer.getId());

        String type = jobOffer.getProject() != null ? "PROJECT" : "INTERNAL";
        String location = "";
        if (jobOffer.getCity() != null && jobOffer.getCity().getName() != null) {
            location = jobOffer.getCity().getName();
        }
        if (jobOffer.getCountry() != null && jobOffer.getCountry().getName() != null) {
            location = location.isBlank() ? jobOffer.getCountry().getName() : location + ", " + jobOffer.getCountry().getName();
        }

        return JobOfferResponseDTO.builder()
                .id(jobOffer.getId())
                .title(jobOffer.getTitle())
                .description(jobOffer.getDescription())
                .contractType(jobOffer.getContractType())
                .deadline(jobOffer.getDeadline())
                .status(jobOffer.getStatus())
                .createdAt(jobOffer.getCreatedAt())
                .updatedAt(jobOffer.getCreatedAt())
                .organizationId(jobOffer.getOrganization() != null ? jobOffer.getOrganization().getId() : null)
                .organizationName(jobOffer.getOrganization() != null ? jobOffer.getOrganization().getName() : null)
                .projectId(jobOffer.getProject() != null ? jobOffer.getProject().getId() : null)
                .projectTitle(jobOffer.getProject() != null ? jobOffer.getProject().getTitle() : null)
                .type(type)
                .location(location)
                .department("INTERNAL".equals(type) ? jobOffer.getContractType() : null)
                .contactEmail(null)
                .contactPerson(null)
                .applicationsCount(totalApplications)
                .totalApplications(totalApplications)
                .build();
    }
}
