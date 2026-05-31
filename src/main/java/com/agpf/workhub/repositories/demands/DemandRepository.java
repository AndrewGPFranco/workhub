package com.agpf.workhub.repositories.demands;

import com.agpf.workhub.dtos.demands.OutputDemandDTO;
import com.agpf.workhub.models.auth.User;
import com.agpf.workhub.models.demands.Demand;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DemandRepository extends JpaRepository<Demand, UUID> {

    @Query(
            value = "select * from demands where user_id = :userId",
            nativeQuery = true
    )
    List<Demand> getDemandsByUser(@Param("userId") Long userId, Pageable pageable);

}
