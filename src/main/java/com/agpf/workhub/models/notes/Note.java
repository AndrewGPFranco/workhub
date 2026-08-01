package com.agpf.workhub.models.notes;

import com.agpf.workhub.models.subdomains.Subdomain;
import com.agpf.workhub.models.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "notes",
        indexes = {
                @Index(
                        name = "idx_notes_user_subdomain_pinned_title",
                        columnList = "user_id, subdomain_id, is_pinned, title"
                )
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_notes_user_subdomain_title",
                        columnNames = {"user_id", "subdomain_id", "title"}
                )
        }
)
public class Note {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "O título é obrigatório.")
    @Column(name = "title", nullable = false, length = 255)
    @Size(max = 255, message = "O título não pode exceder 255 caracteres.")
    private String title;

    @Version
    @Column(name = "version", nullable = false)
    private int version;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "is_archived", nullable = false)
    private boolean isArchived;

    @Column(name = "is_pinned", nullable = false)
    private boolean isPinned;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "O usuário é obrigatório.")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subdomain_id", nullable = false)
    private Subdomain subdomain;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @CreationTimestamp
    @NotNull(message = "A data de criação é obrigatória.")
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
