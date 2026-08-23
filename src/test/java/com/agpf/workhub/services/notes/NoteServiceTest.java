package com.agpf.workhub.services.notes;

import com.agpf.workhub.BaseTest;
import com.agpf.workhub.dtos.notes.RegisterNoteDTO;
import com.agpf.workhub.exceptions.NotFoundException;
import com.agpf.workhub.models.notes.Note;
import com.agpf.workhub.models.subdomains.Subdomain;
import com.agpf.workhub.models.user.User;
import com.agpf.workhub.repositories.notes.NoteRepository;
import com.agpf.workhub.services.subdomains.SubdomainAccessService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
    void shouldCreateNoteInResolvedSubdomain() {
        var user = getUser();
        var subdomain = subdomain(user);
        var note = note(user);
        var dto = new RegisterNoteDTO(note.getTitle(), subdomain.getId(), note.getContent(), null);
        when(subdomainAccessService.resolve(user, subdomain.getId())).thenReturn(subdomain);
        when(noteRepository.save(any(Note.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var result = noteService.register(dto, user);

        var noteCaptor = ArgumentCaptor.forClass(Note.class);
        verify(noteRepository).save(noteCaptor.capture());
        assertEquals(subdomain, noteCaptor.getValue().getSubdomain());
        assertEquals(note.getTitle(), result.title());
        verify(subdomainAccessService).resolve(user, subdomain.getId());
    }

    @Test
    void shouldUpdateOnlyNoteOwnedByAuthenticatedUser() {
        var user = getUser();
        var note = note(user);
        var dto = new RegisterNoteDTO("Título atualizado", null, "Conteúdo atualizado", note.getId());
        when(noteRepository.findByIdAndUser(note.getId(), user)).thenReturn(Optional.of(note));
        when(noteRepository.save(note)).thenReturn(note);

        var result = noteService.register(dto, user);

        assertEquals("Título atualizado", result.title());
        assertEquals("Conteúdo atualizado", result.content());
        verify(noteRepository).save(note);
    }

    @Test
    void shouldNotUpdateNoteThatDoesNotBelongToUser() {
        var user = getUser();
        var id = UUID.randomUUID();
        var dto = new RegisterNoteDTO("Título", null, "Conteúdo", id);
        when(noteRepository.findByIdAndUser(id, user)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> noteService.register(dto, user));

        verify(noteRepository, never()).save(any(Note.class));
    }

    @Test
    void shouldMapNotesToDtosWhenListing() {
        var user = getUser();
        var note = note(user);
        when(noteRepository.buscarNotasDoUsuarioSemSubdominio(any(Long.class), any())).thenReturn(new PageImpl<>(List.of(note)));

        var result = noteService.getBySubdmainAndUser(null, user, 0);

        assertEquals(1, result.content().size());
        assertEquals(note.getId(), result.content().getFirst().id());
    }

    @Test
    void shouldFindOwnedNote() {
        var user = getUser();
        var note = note(user);
        when(noteRepository.findByIdAndUser(note.getId(), user)).thenReturn(Optional.of(note));

        var result = noteService.getNoteByID(note.getId(), user, null);

        assertEquals(note.getId(), result.id());
        assertEquals(note.getContent(), result.content());
    }

    @Test
    void shouldFindOwnedNoteWithinSubdomain() {
        var user = getUser();
        var subdomain = subdomain(user);
        var note = note(user);
        note.setSubdomain(subdomain);
        when(subdomainAccessService.resolve(user, subdomain.getId())).thenReturn(subdomain);
        when(noteRepository.findByIdAndUserAndSubdomain(note.getId(), user, subdomain)).thenReturn(Optional.of(note));

        var result = noteService.getNoteByID(note.getId(), user, subdomain.getId());

        assertEquals(note.getId(), result.id());
        verify(subdomainAccessService).resolve(user, subdomain.getId());
    }

    @Test
    void shouldNotDeleteNoteThatDoesNotBelongToUser() {
        var user = getUser();
        var id = UUID.randomUUID();
        when(noteRepository.findByIdAndUser(id, user)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> noteService.deleteNote(id, user));

        verify(noteRepository, never()).delete(any(Note.class));
    }

    @Test
    void shouldDeleteOwnedNote() {
        var user = getUser();
        var note = note(user);
        when(noteRepository.findByIdAndUser(note.getId(), user)).thenReturn(Optional.of(note));

        var result = noteService.deleteNote(note.getId(), user);

        assertEquals("Anotação deletada com sucesso!", result);
        verify(noteRepository).delete(note);
    }

    private Note note(User user) {
        return new Note(UUID.randomUUID(), "Anotação", 0, null, false, false,
                user, null, "Conteúdo", LocalDateTime.now(), null);
    }

    private Subdomain subdomain(User user) {
        return new Subdomain(UUID.randomUUID(), user, "Trabalho", null, null, null);
    }
}
