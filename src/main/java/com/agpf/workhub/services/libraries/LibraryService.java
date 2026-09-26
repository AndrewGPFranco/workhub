package com.agpf.workhub.services.libraries;

import com.agpf.workhub.models.user.User;
import com.agpf.workhub.services.supabase.StorageService;
import com.agpf.workhub.vo.libraries.FileUploadSbVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class LibraryService {

    private final StorageService storageService;

    public void adicionaNovoArquivo(MultipartFile file, User user) throws IOException {
        validaArquivo(file);
        FileUploadSbVO vo = trataArquivo(user, file);

        storageService.salvaArquivo(vo);

        // TODO: adicionar salvamento dos metadados no banco
    }

    private FileUploadSbVO trataArquivo(User user, MultipartFile file) {
        String path = "/".concat(user.getId().toString())
                .concat("/").concat(file.getOriginalFilename());

        try {
            return new FileUploadSbVO(path, file.getBytes());
        } catch (IOException io) {
            log.error(io.getMessage());
            throw new RuntimeException("Ocorreu um erro ao realizar o upload do arquivo, tente novamente.");
        }
    }

    private void validaArquivo(MultipartFile file) {
        // TODO: corrigir verificação do tamanho e aplicar outras validações.
        if (file.getSize() > 50)
            throw new RuntimeException("O arquivo não pode passar de 50mb");
    }

}
