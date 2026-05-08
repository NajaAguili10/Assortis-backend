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
@Table(name = "conversation_participants", schema = "public")
public class ConversationParticipant {
    private ConversationParticipantId id;

    private Conversation conversation;

    private User user;

    private Instant joinedAt;

    private Boolean isAdmin;

    @EmbeddedId
    public ConversationParticipantId getId() {
        return id;
    }

    @MapsId("conversationId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "conversation_id", nullable = false)
    public Conversation getConversation() {
        return conversation;
    }

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    public User getUser() {
        return user;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "joined_at")
    public Instant getJoinedAt() {
        return joinedAt;
    }

    @ColumnDefault("false")
    @Column(name = "is_admin")
    public Boolean getIsAdmin() {
        return isAdmin;
    }

}
