package com.agpf.workhub.services.notes;

import com.agpf.workhub.dtos.notes.OutputNoteDTO;
import com.agpf.workhub.dtos.notes.RegisterNoteDTO;
import com.agpf.workhub.exceptions.NotFoundException;
import com.agpf.workhub.models.notes.Note;
import com.agpf.workhub.models.user.User;
import com.agpf.workhub.repositories.notes.NoteRepository;
import com.agpf.workhub.services.subdomains.SubdomainAccessService;
import com.agpf.workhub.utils.UtilsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final SubdomainAccessService subdomainAccessService;

    @Transactional
    public Note register(RegisterNoteDTO dto, User user) {
        var subdomain = subdomainAccessService.resolve(user, dto.idSubdomain());

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

    public List<OutputNoteDTO> getNotesBySubdmain(UUID idSubdomain, User user) {
        var subdomain = subdomainAccessService.resolve(user, idSubdomain);
        return noteRepository.findByUserAndSubdomain(user, subdomain);
    }

    public OutputNoteDTO getNoteByID(UUID idNote, User user, UUID idSubdomain) {
        var subdomain = subdomainAccessService.resolve(user, idSubdomain);
        return noteRepository.findByUserAndIdAndSubdomain(user, idNote, subdomain);
    }
}
