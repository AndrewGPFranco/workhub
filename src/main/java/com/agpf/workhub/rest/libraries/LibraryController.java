package com.agpf.workhub.rest.libraries;

import com.agpf.workhub.models.user.User;
import com.agpf.workhub.services.libraries.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/libraries")
public class LibraryController {

    private final LibraryService libraryService;

    @PostMapping(value = "/upload")
    String uploadArquivo(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal User user) throws IOException {
        libraryService.adicionaNovoArquivo(file, user);

        return "Upload realizado com sucesso!";
    }

}
