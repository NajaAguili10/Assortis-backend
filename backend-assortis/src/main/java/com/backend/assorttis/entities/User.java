package com.backend.assorttis.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Map;

@Builder

@Entity
@NoArgsConstructor
@AllArgsConstructor

@Getter
@Setter
@ToString
@Accessors(chain = true)
@Table(name = "users", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "users_email_key", columnNames = { "email" })
})
public class User {
    private Long id;

    private String email;

    private String passwordHash;

    private String firstName;

    private String lastName;

    private String phone;

    private String profilePicture;

    private Language language;

    private Boolean isActive;

    private Boolean emailVerified;

    private Instant lastLoginAt;

    private Country country;

    private City city;

    private Instant termsAcceptedAt;

    private Instant privacyPolicyAcceptedAt;

    private Boolean newsletterOptIn;

    private String linkedinUrl;

    private String whatsapp;

    private Map<String, Object> otherContact;

    private Instant createdAt;

    private Instant deletedAt;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Size(max = 255)
    @NotNull
    @Column(name = "email", nullable = false)
    public String getEmail() {
        return email;
    }

    @Column(name = "password_hash", length = Integer.MAX_VALUE)
    public String getPasswordHash() {
        return passwordHash;
    }

    @Size(max = 100)
    @Column(name = "first_name", length = 100)
    public String getFirstName() {
        return firstName;
    }

    @Size(max = 100)
    @Column(name = "last_name", length = 100)
    public String getLastName() {
        return lastName;
    }

    @Size(max = 30)
    @Column(name = "phone", length = 30)
    public String getPhone() {
        return phone;
    }

    @Column(name = "profile_picture", length = Integer.MAX_VALUE)
    public String getProfilePicture() {
        return profilePicture;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "language")
    public Language getLanguage() {
        return language;
    }

    @ColumnDefault("true")
    @Column(name = "is_active")
    public Boolean getIsActive() {
        return isActive;
    }

    @ColumnDefault("false")
    @Column(name = "email_verified")
    public Boolean getEmailVerified() {
        return emailVerified;
    }

    @Column(name = "last_login_at")
    public Instant getLastLoginAt() {
        return lastLoginAt;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "country_id")
    public Country getCountry() {
        return country;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "city_id")
    public City getCity() {
        return city;
    }

    @Column(name = "terms_accepted_at")
    public Instant getTermsAcceptedAt() {
        return termsAcceptedAt;
    }

    @Column(name = "privacy_policy_accepted_at")
    public Instant getPrivacyPolicyAcceptedAt() {
        return privacyPolicyAcceptedAt;
    }

    @ColumnDefault("false")
    @Column(name = "newsletter_opt_in")
    public Boolean getNewsletterOptIn() {
        return newsletterOptIn;
    }

    @Column(name = "linkedin_url", length = Integer.MAX_VALUE)
    public String getLinkedinUrl() {
        return linkedinUrl;
    }

    @Size(max = 30)
    @Column(name = "whatsapp", length = 30)
    public String getWhatsapp() {
        return whatsapp;
    }

    @Column(name = "other_contact")
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getOtherContact() {
        return otherContact;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

    @Column(name = "deleted_at")
    public Instant getDeletedAt() {
        return deletedAt;
    }

}
