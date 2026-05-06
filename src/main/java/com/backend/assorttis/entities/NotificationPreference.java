package com.backend.assorttis.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "notification_preferences")
public class NotificationPreference {
    @EmbeddedId
    private NotificationPreferenceId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ColumnDefault("true")
    @Column(name = "channel_app")
    private Boolean channelApp;

    @ColumnDefault("false")
    @Column(name = "channel_email")
    private Boolean channelEmail;

    @ColumnDefault("false")
    @Column(name = "channel_newsletter")
    private Boolean channelNewsletter;

}
