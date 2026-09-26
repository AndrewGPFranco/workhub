package com.agpf.workhub.vo.libraries;

// TODO: verificar o warning do equals/hashcode/tostring
public record FileUploadSbVO(
        String path,
        byte[] file
) {
}
