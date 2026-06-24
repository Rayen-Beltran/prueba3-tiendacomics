package com.example.ms_cliente.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.ms_cliente.DTO.EnvioDTO;

@Service
public class EnvioClientService {

    private final WebClient webClient;

    public EnvioClientService(WebClient webClientComic) {
        this.webClient = webClientComic;
    }

    public List<EnvioDTO> obtenerEnvios() {
        return webClient.get()
            .uri("/api/v1/envios")
            .retrieve()
            .onStatus(
                status -> status.is4xxClientError() || status.is5xxServerError(),
                resp -> resp.bodyToMono(String.class)
                    .map(body -> new RuntimeException("Error al consultar envíos: " + body))
            )
            .bodyToFlux(EnvioDTO.class)
            .collectList()
            .block();
    }
}