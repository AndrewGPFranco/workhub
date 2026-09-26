package com.agpf.workhub.config.supabase;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class SupabaseProperties {

    @Value("${supabase.url}")
    private String url;

    @Value("${supabase.secret.key}")
    private String secret;

    @Value("${supabase.storage.bucket}")
    private String storage;

}
