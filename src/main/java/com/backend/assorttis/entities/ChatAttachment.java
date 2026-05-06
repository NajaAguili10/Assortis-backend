package com.backend.assorttis.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "chat_attachments", schema = "public", indexes = {
        @Index(name = "idx_chat_attachments_message", columnList = "message_id")
})
public class ChatAttachment {
    private Long id;

    private Message message;

    private String fileName;

    private String fileUrl;

    private String fileType;

    private BigDecimal fileSizeKb;

    private Instant uploadedAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "message_id", nullable = false)
    public Message getMessage() {
        return message;
    }

    @Size(max = 255)
    @Column(name = "file_name")
    public String getFileName() {
        return fileName;
    }

    @NotNull
    @Column(name = "file_url", nullable = false, length = Integer.MAX_VALUE)
    public String getFileUrl() {
        return fileUrl;
    }

    @Size(max = 50)
    @Column(name = "file_type", length = 50)
    public String getFileType() {
        return fileType;
    }

    @Column(name = "file_size_kb")
    public BigDecimal getFileSizeKb() {
        return fileSizeKb;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "uploaded_at")
    public Instant getUploadedAt() {
        return uploadedAt;
    }

}
