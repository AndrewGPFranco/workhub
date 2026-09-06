package com.agpf.workhub.repositories.sprint;

import com.agpf.workhub.models.sprint.Sprint;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, UUID> {

    Optional<Sprint> findByTitle(@NotNull String sprint);

}
