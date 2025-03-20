package com.viewnext.Practica62Kafka.controller;

import com.viewnext.Practica62Kafka.consumer.ReactiveConsumer;
import com.viewnext.Practica62Kafka.model.Mensaje;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ReactiveController {
    private final ReactiveConsumer reactiveConsumer;

    public ReactiveController(ReactiveConsumer reactiveConsumer) {
        this.reactiveConsumer = reactiveConsumer;
    }

    @GetMapping(value = "/consumeReactivo", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Mensaje> streamMensajes(){
        return reactiveConsumer.pruebaConsumeFluxMensaje();
    }
}
