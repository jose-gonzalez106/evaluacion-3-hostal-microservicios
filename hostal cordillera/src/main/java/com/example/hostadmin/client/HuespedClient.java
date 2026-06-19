package com.example.hostadmin.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.hostadmin.DTO.HuespedDTO;
import com.example.hostadmin.exceptions.RecursoNoEncontradoException;

@Component
public class HuespedClient {

    private final WebClient.Builder webClientBuilder;

    public HuespedClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public HuespedDTO buscarPorRun(String run) {
        return webClientBuilder.build()
                .get()
                .uri("http://huespedes/api/v1/huespedes/{run}", run)
                .retrieve()
                .onStatus(
                        status -> status.value() == 404,
                        response -> response.bodyToMono(String.class)
                                .map(body -> new RecursoNoEncontradoException(
                                        "el huesped " + run + " no existe"))
                )
                .bodyToMono(HuespedDTO.class)
                .block();
    }

}