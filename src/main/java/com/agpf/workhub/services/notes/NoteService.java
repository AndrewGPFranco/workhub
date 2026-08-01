package com.agpf.workhub.services.notes;

import com.agpf.workhub.dtos.http.PageResponseDTO;
import com.agpf.workhub.dtos.notes.OutputNoteDTO;
import com.agpf.workhub.dtos.notes.RegisterNoteDTO;
import com.agpf.workhub.exceptions.NotFoundException;
import com.agpf.workhub.models.notes.Note;
import com.agpf.workhub.models.subdomains.Subdomain;
import com.agpf.workhub.models.user.User;
import com.agpf.workhub.repositories.notes.NoteRepository;
import com.agpf.workhub.services.subdomains.SubdomainAccessService;
import com.agpf.workhub.utils.UtilsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final SubdomainAccessService subdomainAccessService;
    private static final String ANNOTATION_NOT_FOUND = "Anotação não encontrada!";

    @Transactional
    public OutputNoteDTO register(RegisterNoteDTO dto, User user) {
        Subdomain subdomain = null;

        if (dto.idSubdomain() != null)
            subdomain = subdomainAccessService.resolve(user, dto.idSubdomain());

        if (dto.idNote() != null)
            return OutputNoteDTO.fromEntity(updateNote(dto, user));
        else {
            var entity = RegisterNoteDTO.toEntity(dto, user, subdomain);
            return OutputNoteDTO.fromEntity(noteRepository.save(entity));
        }
    }

    private Note updateNote(RegisterNoteDTO dto, User user) {
        var note = noteRepository.findByIdAndUser(dto.idNote(), user).orElseThrow(() -> new NotFoundException(ANNOTATION_NOT_FOUND));

        UtilsService.updateField(dto.title(), note::setTitle);
        UtilsService.updateField(dto.content(), note::setContent);

        return noteRepository.save(note);
    }

    public PageResponseDTO<OutputNoteDTO> getBySubdmainAndUser(UUID idSubdomain, User user, int page) {
        var pageable = PageRequest.of(page, 10);

        if (idSubdomain != null) {
            var subdomain = subdomainAccessService.resolve(user, idSubdomain);
            var notes = noteRepository.findByUserAndSubdomain(user, subdomain, pageable);
            return PageResponseDTO.fromPage(notes.map(OutputNoteDTO::fromEntity));
        }

        return PageResponseDTO.fromPage(noteRepository.findByUser(user, pageable).map(OutputNoteDTO::fromEntity));
    }

    public OutputNoteDTO getNoteByID(UUID idNote, User user, UUID idSubdomain) {
        if (idSubdomain != null) {
            var subdomain = subdomainAccessService.resolve(user, idSubdomain);
            return noteRepository.findByIdAndUserAndSubdomain(idNote, user, subdomain)
                    .map(OutputNoteDTO::fromEntity)
                    .orElseThrow(() -> new NotFoundException(ANNOTATION_NOT_FOUND));
        }

        return noteRepository.findByIdAndUser(idNote, user)
                .map(OutputNoteDTO::fromEntity).orElseThrow(() -> new NotFoundException(ANNOTATION_NOT_FOUND));
    }

    @Transactional
    public String deleteNote(UUID idNote, User user) {
        var note = noteRepository.findByIdAndUser(idNote, user).orElseThrow(() -> new NotFoundException(ANNOTATION_NOT_FOUND));
        noteRepository.delete(note);
        return "Anotação deletada com sucesso!";
    }
}
