package com.backend.assorttis.dto.training;

import java.time.Instant;
import java.time.LocalDate;

public class OrganizationCertificationResponseDto {

    private Long certificationId;
    private Long courseId;
    private String courseTitle;
    private String certificationName;
    private String issuingOrganization;
    private LocalDate completedOn;
    private LocalDate expiryDate;
    private String credentialId;
    private String credentialUrl;
    private String downloadUrl;
    private Instant createdAt;

    private Long userId;
    private String userFirstName;
    private String userLastName;
    private String userEmail;

    private Boolean certified;

    public OrganizationCertificationResponseDto(
            Long certificationId,
            Long courseId,
            String courseTitle,
            String certificationName,
            String issuingOrganization,
            LocalDate completedOn,
            LocalDate expiryDate,
            String credentialId,
            String credentialUrl,
            Instant createdAt,
            Long userId,
            String userFirstName,
            String userLastName,
            String userEmail
    ) {
        this.certificationId = certificationId;
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.certificationName = certificationName;
        this.issuingOrganization = issuingOrganization;
        this.completedOn = completedOn;
        this.expiryDate = expiryDate;
        this.credentialId = credentialId;
        this.credentialUrl = credentialUrl;
        this.createdAt = createdAt;
        this.userId = userId;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userEmail = userEmail;
        this.certified = true;
        this.downloadUrl = "/api/training/portfolio/certifications/" + certificationId + "/download";
    }

    public Long getCertificationId() {
        return certificationId;
    }

    public void setCertificationId(Long certificationId) {
        this.certificationId = certificationId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCertificationName() {
        return certificationName;
    }

    public void setCertificationName(String certificationName) {
        this.certificationName = certificationName;
    }

    public String getIssuingOrganization() {
        return issuingOrganization;
    }

    public void setIssuingOrganization(String issuingOrganization) {
        this.issuingOrganization = issuingOrganization;
    }

    public LocalDate getCompletedOn() {
        return completedOn;
    }

    public void setCompletedOn(LocalDate completedOn) {
        this.completedOn = completedOn;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(String credentialId) {
        this.credentialId = credentialId;
    }

    public String getCredentialUrl() {
        return credentialUrl;
    }

    public void setCredentialUrl(String credentialUrl) {
        this.credentialUrl = credentialUrl;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Boolean getCertified() {
        return certified;
    }

    public void setCertified(Boolean certified) {
        this.certified = certified;
    }
}
