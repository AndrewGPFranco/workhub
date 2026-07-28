package com.agpf.workhub.models.demands;

import com.agpf.workhub.enums.demands.PriorityDemandType;
import com.agpf.workhub.enums.demands.StatusDemandType;
import com.agpf.workhub.models.subdomains.Subdomain;
import com.agpf.workhub.models.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "demands", indexes = {
        @Index(name = "idx_demands_title", columnList = "title")
})
public class Demand {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull(message = "O título é obrigatório.")
    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @NotNull(message = "A descrição é obrigatória.")
    @Column(name = "description", length = 1000, nullable = false)
    private String description;

    @Column(name = "deadline")
    private LocalDate deadline;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "O status é obrigatório.")
    @Column(name = "status", nullable = false, length = 20)
    private StatusDemandType status;

    @CreationTimestamp
    @NotNull(message = "A data de criação é obrigatória.")
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "finalized_at")
    private LocalDate finalizedAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "A prioridade é obrigatória.")
    @Column(name = "priority", nullable = false, length = 20)
    private PriorityDemandType priority;

    @Column(name = "observations_to_review", nullable = true, length = 2500, columnDefinition = "TEXT")
    private String observationsToReview;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subdomain_id")
    private Subdomain subdomain;

    @OneToMany(mappedBy = "demand", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<Observation> observations;

}
