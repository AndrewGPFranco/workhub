package com.agpf.workhub.repositories.user;

import com.agpf.workhub.models.user.Feedback;
import com.agpf.workhub.models.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, UUID> {

    Page<Feedback> findByUser(User user, Pageable pageable);

}
