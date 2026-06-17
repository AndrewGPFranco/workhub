package com.agpf.workhub.repositories.user;

import com.agpf.workhub.models.user.Feedback;
import com.agpf.workhub.models.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, UUID> {

    Page<Feedback> findByUser(User user, Pageable pageable);

    @Query("""
            SELECT f
            FROM Feedback f
            WHERE f.user = :user
              AND ((:subdomainId IS NULL AND f.subdomain IS NULL) OR f.subdomain.id = :subdomainId)
            ORDER BY f.date DESC
            """)
    Page<Feedback> findByUserAndSubdomain(
            @Param("user") User user,
            @Param("subdomainId") UUID subdomainId,
            Pageable pageable
    );

}
