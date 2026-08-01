package com.agpf.workhub.repositories.notes;

import com.agpf.workhub.dtos.notes.OutputNoteDTO;
import com.agpf.workhub.models.notes.Note;
import com.agpf.workhub.models.subdomains.Subdomain;
import com.agpf.workhub.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NoteRepository extends JpaRepository<Note, UUID> {

    @Query(
            """
                    select new com.agpf.workhub.dtos.notes.OutputNoteDTO(
                        n.id, n.title, n.isArchived, n.isPinned, n.content, n.createdAt, n.updatedAt
                    ) from Note n where n.user = :user and n.subdomain = :subdomain
                    """
    )
    List<OutputNoteDTO> findByUserAndSubdomain(@Param("user") User user, @Param("subdomain") Subdomain subdomain);

    @Query(
            """
                    select new com.agpf.workhub.dtos.notes.OutputNoteDTO(
                        n.id, n.title, n.isArchived, n.isPinned, n.content, n.createdAt, n.updatedAt
                    ) from Note n where n.user = :user and n.id = :idNote and n.subdomain = :subdomain
                    """
    )
    OutputNoteDTO findByUserAndIdAndSubdomain(
            @Param("user") User user, @Param("idNote") UUID idNote, @Param("subdomain") Subdomain subdomain);

    @Query(
            """
                    select new com.agpf.workhub.dtos.notes.OutputNoteDTO(
                        n.id, n.title, n.isArchived, n.isPinned, n.content, n.createdAt, n.updatedAt
                    ) from Note n where n.user = :user
                    """
    )
    List<OutputNoteDTO> findByUser(@Param("user") User user);

    @Query(
            """
                    select new com.agpf.workhub.dtos.notes.OutputNoteDTO(
                        n.id, n.title, n.isArchived, n.isPinned, n.content, n.createdAt, n.updatedAt
                    ) from Note n where n.user = :user and n.id = :idNote
                    """
    )
    OutputNoteDTO findByUserAndId(@Param("user") User user, @Param("idNote") UUID idNote);

    UUID id(UUID id);
}
