package com.agpf.workhub.models.library;

import com.agpf.workhub.models.BaseEntity;
import com.agpf.workhub.models.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "libraries",
        indexes = {
                @Index(name = "idx_library_name", columnList = "name")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uc_library_name_path_user", columnNames = {
                        "name", "path", "user"
                })
        }
)
public class Library extends BaseEntity {

    @Column(name = "name", nullable = false, length = 35)
    @NotBlank(message = "O nome do arquivo obrigatório!")
    private String name;

    @Column(name = "path", nullable = false)
    @NotBlank(message = "O caminho para o arquivo é obrigatório!")
    private String path;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

}
