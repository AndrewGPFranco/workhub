package com.agpf.workhub.repositories.demands;

import com.agpf.workhub.enums.demands.PriorityDemandType;
import com.agpf.workhub.enums.demands.SprintType;
import com.agpf.workhub.enums.demands.StatusDemandType;
import com.agpf.workhub.models.demands.Demand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DemandRepository extends JpaRepository<Demand, UUID> {

    @Query("""
            SELECT d
            FROM Demand d
            WHERE d.user.id = :userId
              AND ((:idSubdomain IS NULL AND d.subdomain IS NULL) OR d.subdomain.id = :idSubdomain)
              AND (:status IS NULL OR d.status = :status)
              AND (:priority IS NULL OR d.priority = :priority)
            ORDER BY d.createdAt DESC
            """)
    Page<Demand> findByUserAndSubdomainAndFilters(
            @Param("userId") Long userId, @Param("idSubdomain") UUID idSubdomain,
            @Param("status") StatusDemandType status, @Param("priority") PriorityDemandType priority, Pageable pageable);

    @Query(
            value = """
                    SELECT *
                    FROM demands
                    WHERE user_id = :idUser
                      AND ((:idSubdomain IS NULL AND subdomain_id IS NULL) OR subdomain_id = :idSubdomain)
                      AND UPPER(title) LIKE CONCAT('%', UPPER(:title), '%')
                    """,
            nativeQuery = true
    )
    List<Demand> searchByDemand(@Param("title") String title, @Param("idUser") Long idUser, @Param("idSubdomain") UUID idSubdomain);

    @Query("""
            SELECT d
            FROM Demand d
            WHERE d.user.id = :userId AND d.sprint = :sprint
                        AND (:priority IS NULL OR d.priority = :priority)
              AND ((:idSubdomain IS NULL AND d.subdomain IS NULL) OR d.subdomain.id = :idSubdomain)
            ORDER BY d.createdAt DESC
            """)
    Page<Demand> buscarDemandsPorSprint(@Param("userId") Long userId, @Param("idSubdomain") UUID idSubdomain,
                                        @Param("priority") PriorityDemandType priority, @Param("sprint") SprintType sprint, Pageable pageable);
}
