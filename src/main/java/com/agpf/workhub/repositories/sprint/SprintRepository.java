package com.agpf.workhub.repositories.sprint;

import com.agpf.workhub.dtos.sprints.InputDemandsToSprintDTO;
import com.agpf.workhub.models.sprint.Sprint;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, UUID> {

    Optional<Sprint> findByTitle(@NotNull String sprint);

    @Query(value = """
            select title from sprints where user_id = :idUser and subdomain_id = :idSubdomain
            """, nativeQuery = true)
    List<String> getByUserAndSubdomain(@Param("idUser") Long idUser, @Param("idSubdomain") UUID idSubdomain);
}
