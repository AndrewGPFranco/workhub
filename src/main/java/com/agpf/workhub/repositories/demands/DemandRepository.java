package com.agpf.workhub.repositories.demands;

import com.agpf.workhub.models.demands.Demand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DemandRepository extends JpaRepository<Demand, UUID> {

    Page<Demand> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

}
