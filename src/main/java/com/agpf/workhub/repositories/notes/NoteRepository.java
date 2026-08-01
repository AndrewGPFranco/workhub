package com.agpf.workhub.repositories.notes;

import com.agpf.workhub.models.notes.Note;
import com.agpf.workhub.models.subdomains.Subdomain;
import com.agpf.workhub.models.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface NoteRepository extends JpaRepository<Note, UUID> {

    Page<Note> findByUserAndSubdomain(User user, Subdomain subdomain, Pageable pageable);

    Page<Note> findByUser(User user, Pageable pageable);

    Optional<Note> findByIdAndUser(UUID id, User user);

    Optional<Note> findByIdAndUserAndSubdomain(UUID id, User user, Subdomain subdomain);
}
