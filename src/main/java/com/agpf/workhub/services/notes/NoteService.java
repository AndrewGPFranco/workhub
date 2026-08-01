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

    @Transactional
    public Note register(RegisterNoteDTO dto, User user) {
        Subdomain subdomain = null;

        if (dto.idSubdomain() != null)
            subdomain = subdomainAccessService.resolve(user, dto.idSubdomain());

        if (dto.idNote() != null)
            return updateNote(dto);
        else {
            var entity = RegisterNoteDTO.toEntity(dto, user, subdomain);
            return noteRepository.save(entity);
        }
    }

    private Note updateNote(RegisterNoteDTO dto) {
        var note = noteRepository.findById(dto.idNote()).orElseThrow(() -> new NotFoundException("Anotação não encontrada!"));

        UtilsService.updateField(dto.title(), note::setTitle);
        UtilsService.updateField(dto.content(), note::setContent);

        return noteRepository.save(note);
    }

    public PageResponseDTO<OutputNoteDTO> getBySubdmainAndUser(UUID idSubdomain, User user, int page) {
        var pageable = PageRequest.of(page, 10);

        if (idSubdomain != null) {
            var subdomain = subdomainAccessService.resolve(user, idSubdomain);
            var notes = noteRepository.findByUserAndSubdomain(user, subdomain, pageable);
            return PageResponseDTO.fromPage(notes);
        }

        return PageResponseDTO.fromPage(noteRepository.findByUser(user, pageable));
    }

    public OutputNoteDTO getNoteByID(UUID idNote, User user, UUID idSubdomain) {
        if (idSubdomain != null) {
            var subdomain = subdomainAccessService.resolve(user, idSubdomain);
            return noteRepository.findByUserAndIdAndSubdomain(user, idNote, subdomain);
        }

        return noteRepository.findByUserAndId(user, idNote);
    }

    @Transactional
    public String deleteNote(UUID idNote) {
        noteRepository.deleteById(idNote);
        return "Anotação deletada com sucesso!";
    }
}
