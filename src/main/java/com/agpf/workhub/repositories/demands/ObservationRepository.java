package com.agpf.workhub.repositories.demands;

import com.agpf.workhub.models.demands.Observation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ObservationRepository extends JpaRepository<Observation, UUID> {
}
