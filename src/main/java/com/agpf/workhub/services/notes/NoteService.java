package com.agpf.workhub.services.notes;

import com.agpf.workhub.dtos.notes.OutputNoteDTO;
import com.agpf.workhub.dtos.notes.RegisterNoteDTO;
import com.agpf.workhub.models.user.User;
import com.agpf.workhub.repositories.notes.NoteRepository;
import com.agpf.workhub.services.subdomains.SubdomainAccessService;
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
    public void register(RegisterNoteDTO dto, User user) {
        var subdomain = subdomainAccessService.resolve(user, dto.idSubdomain());

        var entity = RegisterNoteDTO.toEntity(dto, user, subdomain);

        noteRepository.save(entity);
    }

    public List<OutputNoteDTO> getNotesBySubdmain(UUID idSubdomain, User user) {
        var subdomain = subdomainAccessService.resolve(user, idSubdomain);
        return noteRepository.findByUserAndSubdomain(user, subdomain);
    }
}
