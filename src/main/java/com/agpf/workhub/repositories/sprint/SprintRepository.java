package com.agpf.workhub.repositories.sprint;

import com.agpf.workhub.models.sprint.Sprint;
import com.agpf.workhub.models.user.User;
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

    Optional<Sprint> findByTitleAndUser(@NotNull String sprint, @NotNull User user);

    @Query(value = """
            select title from sprints where user_id = :idUser and subdomain_id = :idSubdomain
            """, nativeQuery = true)
    List<String> getByUserAndSubdomainNull(@Param("idUser") Long idUser, @Param("idSubdomain") UUID idSubdomain);

    @Query(value = """
            select title from sprints where user_id = :idUser and subdomain_id is null
            """, nativeQuery = true)
    List<String> getByUserAndSubdomainNull(@Param("idUser") Long idUser);
}
