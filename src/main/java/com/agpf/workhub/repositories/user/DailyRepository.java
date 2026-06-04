package com.agpf.workhub.repositories.user;

import com.agpf.workhub.models.user.Daily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface DailyRepository extends JpaRepository<Daily, UUID> {

    @Query(
            value = "select * from daily where user_id = :idUser and date_summary = :dateSummary",
            nativeQuery = true
    )
    Daily findByUserAndDateSummary(@Param("dateSummary") LocalDate dateSummary, @Param("idUser") Long idUser);

}
