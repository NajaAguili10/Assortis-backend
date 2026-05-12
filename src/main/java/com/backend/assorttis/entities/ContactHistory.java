package com.backend.assorttis.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;

@Entity
@Table(name = "contact_history")
@Getter @Setter @NoArgsConstructor
public class ContactHistory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_offer_id", nullable = false)
    private JobOffer jobOffer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender;

    private String subject;

    @Column(length = 4000)
    private String message;

    @Column(name = "contact_date")
    private Instant contactDate = Instant.now();

    @Column(length = 20)
    private String status; // SENT, DELIVERED, READ, REPLIED, ARCHIVED

    @Column(name = "recipient_email")
    private String recipientEmail;

    @Column(name = "read_date")
    private Instant readDate;

    @Column(name = "reply_date")
    private Instant replyDate;

    @Column(name = "reply_message", length = 4000)
    private String replyMessage;
}
