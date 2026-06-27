package com.example.ms_cliente.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient webClientComic() {
        return WebClient.builder()
            .baseUrl("http://localhost:8082")
            .build();
    }
}