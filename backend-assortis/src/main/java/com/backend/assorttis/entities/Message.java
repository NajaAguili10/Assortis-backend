package com.backend.assorttis.entities;

import jakarta.persistence.*;
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
@Table(name = "messages", schema = "public")
public class Message {
    private Long id;

    private Conversation conversation;

    private User sender;

    private String content;

    private String attachmentUrl;

    private Boolean isRead;

    private Instant createdAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "conversation_id")
    public Conversation getConversation() {
        return conversation;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "sender_id")
    public User getSender() {
        return sender;
    }

    @Column(name = "content", length = Integer.MAX_VALUE)
    public String getContent() {
        return content;
    }

    @Column(name = "attachment_url", length = Integer.MAX_VALUE)
    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    @ColumnDefault("false")
    @Column(name = "is_read")
    public Boolean getIsRead() {
        return isRead;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

}
