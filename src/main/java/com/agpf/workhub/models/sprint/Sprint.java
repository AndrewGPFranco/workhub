package com.agpf.workhub.models.sprint;

import com.agpf.workhub.models.BaseEntity;
import com.agpf.workhub.models.subdomains.Subdomain;
import com.agpf.workhub.models.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "sprints",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_sprint_title_subdomain",
                        columnNames = {"title", "subdomain_id"}
                )
        }
)
public class Sprint extends BaseEntity {

    @Column(name = "title", nullable = false, length = 20)
    @NotBlank(message = "É necessário informar um título.")
    private String title;

    @Column(name = "date_to_use", nullable = false)
    @NotNull(message = "A data de uso é obrigatória.")
    private LocalDate dateToUse;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "O usuário é obrigatório.")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subdomain_id", nullable = false)
    private Subdomain subdomain;

}
