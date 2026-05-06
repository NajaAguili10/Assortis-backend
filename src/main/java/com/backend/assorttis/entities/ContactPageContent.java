package com.backend.assorttis.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "contact_page_content", schema = "public")
public class ContactPageContent {
    private Integer id;

    private Map<String, Object> bannerTitle;

    private Map<String, Object> bannerSubtitle;

    private Map<String, Object> workingHoursTitle;

    private Map<String, Object> workingHoursSchedule;

    private Map<String, Object> formTitle;

    private Map<String, Object> formSubtitle;

    private Map<String, Object> submitButtonLabel;

    private Map<String, Object> submittingButtonLabel;

    private Map<String, Object> successMessage;

    private Map<String, Object> errorMessage;

    private Instant updatedAt;

    @Id
    @ColumnDefault("1")
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    @Column(name = "banner_title")
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getBannerTitle() {
        return bannerTitle;
    }

    @Column(name = "banner_subtitle")
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getBannerSubtitle() {
        return bannerSubtitle;
    }

    @Column(name = "working_hours_title")
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getWorkingHoursTitle() {
        return workingHoursTitle;
    }

    @Column(name = "working_hours_schedule")
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getWorkingHoursSchedule() {
        return workingHoursSchedule;
    }

    @Column(name = "form_title")
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getFormTitle() {
        return formTitle;
    }

    @Column(name = "form_subtitle")
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getFormSubtitle() {
        return formSubtitle;
    }

    @Column(name = "submit_button_label")
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getSubmitButtonLabel() {
        return submitButtonLabel;
    }

    @Column(name = "submitting_button_label")
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getSubmittingButtonLabel() {
        return submittingButtonLabel;
    }

    @Column(name = "success_message")
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getSuccessMessage() {
        return successMessage;
    }

    @Column(name = "error_message")
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getErrorMessage() {
        return errorMessage;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    public Instant getUpdatedAt() {
        return updatedAt;
    }

}
