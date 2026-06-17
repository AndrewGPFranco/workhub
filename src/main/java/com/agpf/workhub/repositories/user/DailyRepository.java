package com.agpf.workhub.repositories.user;

import com.agpf.workhub.models.user.Daily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface DailyRepository extends JpaRepository<Daily, UUID> {

    @Query(
            value = """
                    SELECT *
                    FROM daily
                    WHERE user_id = :idUser
                      AND ((:subdomainId IS NULL AND subdomain_id IS NULL) OR subdomain_id = :subdomainId)
                      AND date_summary BETWEEN :startDate AND :endDate
                    """,
            nativeQuery = true
    )
    List<Daily> findByUserBetweenDates(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("idUser") Long idUser,
            @Param("subdomainId") UUID subdomainId
    );

}
