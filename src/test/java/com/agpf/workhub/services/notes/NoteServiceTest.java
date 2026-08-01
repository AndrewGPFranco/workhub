package com.agpf.workhub.services.notes;

import com.agpf.workhub.BaseTest;
import com.agpf.workhub.dtos.notes.RegisterNoteDTO;
import com.agpf.workhub.exceptions.NotFoundException;
import com.agpf.workhub.models.notes.Note;
import com.agpf.workhub.models.user.User;
import com.agpf.workhub.repositories.notes.NoteRepository;
import com.agpf.workhub.services.subdomains.SubdomainAccessService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest extends BaseTest {

    @InjectMocks
    private NoteService noteService;

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private SubdomainAccessService subdomainAccessService;

    @Test
    void shouldReturnDtoWhenRegisteringNote() {
        var user = getUser();
        var note = note(user);
        var dto = new RegisterNoteDTO(note.getTitle(), null, note.getContent(), null);
        when(noteRepository.save(any(Note.class))).thenReturn(note);

        var result = noteService.register(dto, user);

        assertEquals(note.getId(), result.id());
        assertEquals(note.getTitle(), result.title());
        verify(noteRepository).save(any(Note.class));
    }

    @Test
    void shouldMapNotesToDtosWhenListing() {
        var user = getUser();
        var note = note(user);
        when(noteRepository.findByUser(any(User.class), any())).thenReturn(new PageImpl<>(List.of(note)));

        var result = noteService.getBySubdmainAndUser(null, user, 0);

        assertEquals(1, result.content().size());
        assertEquals(note.getId(), result.content().getFirst().id());
    }

    @Test
    void shouldNotDeleteNoteThatDoesNotBelongToUser() {
        var user = getUser();
        var id = UUID.randomUUID();
        when(noteRepository.findByIdAndUser(id, user)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> noteService.deleteNote(id, user));

        verify(noteRepository, never()).delete(any(Note.class));
    }

    private Note note(User user) {
        return new Note(UUID.randomUUID(), "Anotação", 0, null, false, false,
                user, null, "Conteúdo", LocalDateTime.now(), null);
    }
}
