package com.agpf.workhub.models.user;

import com.agpf.workhub.models.subdomains.Subdomain;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "feedbacks")
public class Feedback {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "date_feedback", nullable = false)
    @NotNull(message = "É necessário informar a data do feedback!")
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "month", nullable = false)
    @NotNull(message = "É necessário informar o mes do feedback!")
    private Month month;

    @NotNull(message = "É necessário informar um feedback!")
    @Column(name = "summary", nullable = false, columnDefinition = "TEXT")
    private String summary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank(message = "É necessário informar a pessoa que aplicou o feedback!")
    @Column(name = "people_feedback", nullable = false)
    private String peopleFeedback;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subdomain_id")
    private Subdomain subdomain;

}
