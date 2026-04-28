package com.backend.assorttis.dto.training;

import com.backend.assorttis.enums.CourseLanguage;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CourseCatalogResponseDto {

    private Long id;
    private String title;
    private String description;

    private String level;
    private String deliveryMode;
    private Integer durationHours;
    private Integer modulesCount;
    private LocalDate startDate;
    private CourseLanguage courseLanguage;

    private BigDecimal price;
    private String currency;
    private Boolean isFree;
    private String status;
    private String thumbnailUrl;

    private Boolean certificationAvailable;
    private BigDecimal certificationPrice;
    private String certificationTitle;
    private String certificationIssuer;
    private Integer certificationValidityMonths;

    private String tags;

    private Long expertId;
    private String expertName;

    private List<CourseSectorSummaryDto> sectors = new ArrayList<>();

    private Instant createdAt;

    public CourseCatalogResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public CourseCatalogResponseDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public CourseCatalogResponseDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CourseCatalogResponseDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getLevel() {
        return level;
    }

    public CourseCatalogResponseDto setLevel(String level) {
        this.level = level;
        return this;
    }

    public String getDeliveryMode() {
        return deliveryMode;
    }

    public CourseCatalogResponseDto setDeliveryMode(String deliveryMode) {
        this.deliveryMode = deliveryMode;
        return this;
    }

    public Integer getDurationHours() {
        return durationHours;
    }

    public CourseCatalogResponseDto setDurationHours(Integer durationHours) {
        this.durationHours = durationHours;
        return this;
    }

    public Integer getModulesCount() {
        return modulesCount;
    }

    public CourseCatalogResponseDto setModulesCount(Integer modulesCount) {
        this.modulesCount = modulesCount;
        return this;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public CourseCatalogResponseDto setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public CourseLanguage getCourseLanguage() {
        return courseLanguage;
    }

    public CourseCatalogResponseDto setCourseLanguage(CourseLanguage courseLanguage) {
        this.courseLanguage = courseLanguage;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public CourseCatalogResponseDto setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public CourseCatalogResponseDto setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public Boolean getIsFree() {
        return isFree;
    }

    public CourseCatalogResponseDto setIsFree(Boolean free) {
        isFree = free;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public CourseCatalogResponseDto setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public CourseCatalogResponseDto setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
        return this;
    }

    public Boolean getCertificationAvailable() {
        return certificationAvailable;
    }

    public CourseCatalogResponseDto setCertificationAvailable(Boolean certificationAvailable) {
        this.certificationAvailable = certificationAvailable;
        return this;
    }

    public BigDecimal getCertificationPrice() {
        return certificationPrice;
    }

    public CourseCatalogResponseDto setCertificationPrice(BigDecimal certificationPrice) {
        this.certificationPrice = certificationPrice;
        return this;
    }

    public String getCertificationTitle() {
        return certificationTitle;
    }

    public CourseCatalogResponseDto setCertificationTitle(String certificationTitle) {
        this.certificationTitle = certificationTitle;
        return this;
    }

    public String getCertificationIssuer() {
        return certificationIssuer;
    }

    public CourseCatalogResponseDto setCertificationIssuer(String certificationIssuer) {
        this.certificationIssuer = certificationIssuer;
        return this;
    }

    public Integer getCertificationValidityMonths() {
        return certificationValidityMonths;
    }

    public CourseCatalogResponseDto setCertificationValidityMonths(Integer certificationValidityMonths) {
        this.certificationValidityMonths = certificationValidityMonths;
        return this;
    }

    public String getTags() {
        return tags;
    }

    public CourseCatalogResponseDto setTags(String tags) {
        this.tags = tags;
        return this;
    }

    public Long getExpertId() {
        return expertId;
    }

    public CourseCatalogResponseDto setExpertId(Long expertId) {
        this.expertId = expertId;
        return this;
    }

    public String getExpertName() {
        return expertName;
    }

    public CourseCatalogResponseDto setExpertName(String expertName) {
        this.expertName = expertName;
        return this;
    }

    public List<CourseSectorSummaryDto> getSectors() {
        return sectors;
    }

    public CourseCatalogResponseDto setSectors(List<CourseSectorSummaryDto> sectors) {
        this.sectors = sectors;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public CourseCatalogResponseDto setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
