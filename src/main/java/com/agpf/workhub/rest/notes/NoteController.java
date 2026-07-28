package com.agpf.workhub.rest.notes;

import com.agpf.workhub.annotations.PlanResource;
import com.agpf.workhub.dtos.http.ResponseAPI;
import com.agpf.workhub.dtos.notes.RegisterNoteDTO;
import com.agpf.workhub.enums.plan.PlanResourceType;
import com.agpf.workhub.models.notes.Note;
import com.agpf.workhub.models.user.User;
import com.agpf.workhub.services.notes.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notes")
@PlanResource(verify = PlanResourceType.NOTES)
public class NoteController {

    private final NoteService noteService;

    @GetMapping(value = "/{idSubdomain}")
    ResponseEntity<ResponseAPI> getBySubdmain(@PathVariable UUID idSubdomain, @AuthenticationPrincipal User user) {
        var notes = noteService.getNotesBySubdmain(idSubdomain, user);
        return ResponseEntity.ok().body(new ResponseAPI(HttpStatus.OK.value(), notes));
    }

    @GetMapping(value = "/{idSubdomain}/{idNote}")
    ResponseEntity<ResponseAPI> getNoteByID(@PathVariable UUID idNote,
                                                   @PathVariable UUID idSubdomain, @AuthenticationPrincipal User user) {
        var note = noteService.getNoteByID(idNote, user, idSubdomain);
        return ResponseEntity.ok().body(new ResponseAPI(HttpStatus.OK.value(), note));
    }

    @PostMapping(value = "/register")
    ResponseEntity<ResponseAPI> register(@RequestBody @Valid RegisterNoteDTO dto, @AuthenticationPrincipal User user) {
        var note = noteService.register(dto, user);
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(new ResponseAPI(HttpStatus.CREATED.value(), note));
    }

}
