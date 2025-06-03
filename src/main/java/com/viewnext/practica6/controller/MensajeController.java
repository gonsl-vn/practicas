package com.viewnext.practica6.controller;

import com.viewnext.practica6.model.Mensaje;
import com.viewnext.practica6.producer.KafkaMensajeProducer;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

/**
 * The type Mensaje controller.
 */
@RestController
@RequestMapping("/mensajes") // Ruta inicial: "/mensajes"
public class MensajeController {

    private final Sinks.Many<Mensaje> sink;
    private final KafkaMensajeProducer kafkaProducer;

    /**
     * Instantiates a new Mensaje controller.
     *
     * @param kStream
     *         the k stream
     * @param kafkaProducer
     *         the kafka producer
     */
    public MensajeController(KStream<String, Mensaje> kStream, KafkaMensajeProducer kafkaProducer) {
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
        this.kafkaProducer = kafkaProducer;

        kStream.foreach((key, mensaje) -> sink.tryEmitNext(mensaje));
    }

    /**
     * Stream mensajes flux.
     *
     * @return the flux
     */
    // Método Get para poder obtener los mensajes con stream. La ruta es: "/mensajes/stream"
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Mensaje> streamMensajes() {
        return sink.asFlux();
    }

    /**
     * Send mensaje response entity.
     *
     * @param mensaje
     *         the mensaje
     * @return the response entity
     */
    //  Método Post para poder envair los mensajes (tiene que ser en formato Json). La ruta es: "/mensajes/send"
    @PostMapping("/send")
    public ResponseEntity<String> sendMensaje(@RequestBody Mensaje mensaje) {
        kafkaProducer.sendMensaje("topic1", mensaje);
        return ResponseEntity.ok("Mensaje enviado a Kafka");
    }
}
