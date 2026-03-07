package com.agpf.workhub.infrastructure.adapters.out.persistence;

import com.agpf.workhub.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String username);

    Optional<User> findByUsername(String username);

}
