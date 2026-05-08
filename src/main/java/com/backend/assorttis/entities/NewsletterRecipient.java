package com.backend.assorttis.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "newsletter_recipients", schema = "public")
public class NewsletterRecipient {
    private Long id;

    private NewsletterCampaign campaign;

    private User user;

    private String status;

    private Instant sentAt;

    private Boolean opened;

    private Boolean clicked;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "campaign_id")
    public NewsletterCampaign getCampaign() {
        return campaign;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    @Size(max = 50)
    @ColumnDefault("'pending'")
    @Column(name = "status", length = 50)
    public String getStatus() {
        return status;
    }

    @Column(name = "sent_at")
    public Instant getSentAt() {
        return sentAt;
    }

    @ColumnDefault("false")
    @Column(name = "opened")
    public Boolean getOpened() {
        return opened;
    }

    @ColumnDefault("false")
    @Column(name = "clicked")
    public Boolean getClicked() {
        return clicked;
    }

}
