package com.agpf.workhub.services.supabase;

import com.agpf.workhub.vo.libraries.FileUploadSbVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class StorageService {

    @Qualifier("supabaseRestClient")
    private final RestClient restClient;

    public void salvaArquivo(FileUploadSbVO vo) {
        restClient.post()
                .uri(vo.path())
                .contentType(MediaType.TEXT_PLAIN).body(vo.file()).retrieve().toBodilessEntity();
    }

}
