package com.agpf.workhub.repositories.demands;

import com.agpf.workhub.enums.demands.PriorityDemandType;
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

    Page<Demand> findByUserIdOrderByCreatedAtDesc(Long idUser, Pageable pageable);

    Page<Demand> findByUserIdAndStatusOrderByCreatedAtDesc(Long userId, StatusDemandType status, Pageable pageable);

    Page<Demand> findByUserIdAndPriorityOrderByCreatedAtDesc(Long userId, PriorityDemandType priority, Pageable pageable);

    Page<Demand> findByUserIdAndPriorityAndStatusOrderByCreatedAtDesc(
            Long userId, PriorityDemandType priority, StatusDemandType status, Pageable pageable
    );

    @Query(
            value = """
              SELECT *
              FROM demands
              WHERE user_id = :idUser
                AND UPPER(title) LIKE CONCAT('%', UPPER(:title), '%')
              """,
            nativeQuery = true
    )
    List<Demand> searchByDemand(@Param("title") String title, @Param("idUser") Long idUser);
}
