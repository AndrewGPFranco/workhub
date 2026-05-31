package com.agpf.workhub.dtos.http;

import org.springframework.data.domain.Page;

import java.util.List;

public record PageResponseDTO<T>(
        List<T> content,
        int page,
        int pageSize,
        int totalPages,
        long totalElements,
        boolean hasNext
) {

    public static <T> PageResponseDTO<T> fromPage(Page<T> page) {
        return new PageResponseDTO<>(page.getContent(), page.getNumber(),
                page.getSize(), page.getTotalPages(), page.getTotalElements(), page.hasNext());
    }

}
