package com.viewnext.practicas.P4SeriesYPeliculas.clients;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class UserClient {
    private final WebClient webClient;

    public UserClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("http://localhost:8081/usuarios/existe").build();
    }

    public boolean existeUsuario(String dni) {
        return webClient.get().uri("/{dni}", dni)
                .retrieve().bodyToMono(Boolean.class).block();
    }
}
