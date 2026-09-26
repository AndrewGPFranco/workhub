package com.agpf.workhub.config.supabase;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class SupabaseConfig {

    private final SupabaseProperties supabaseProperties;

    public SupabaseConfig(SupabaseProperties supabaseProperties) {
        this.supabaseProperties = supabaseProperties;
    }

    @Bean("supabaseRestClient")
    public RestClient restClient() {
        return RestClient.builder()
                .defaultHeader("apikey", supabaseProperties.getSecret())
                .baseUrl(supabaseProperties.getUrl()).build();
    }

}
