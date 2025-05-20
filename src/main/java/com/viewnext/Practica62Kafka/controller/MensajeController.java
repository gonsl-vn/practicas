package com.viewnext.Practica62Kafka.controller;

import com.viewnext.Practica62Kafka.model.MensajeConNombreUsu;
import com.viewnext.Practica62Kafka.producer.MensajeProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MensajeController {

    private final MensajeProducer mensajeProducer;

    @PostMapping("/enviaMensaje")
    public ResponseEntity<String> enviaMensaje(
            @RequestBody MensajeConNombreUsu mensaje){
        log.info("Mensaje En BACKEND: " + mensaje);
        mensajeProducer.convertirAMensajeYEnviar(mensaje);
        return  ResponseEntity.ok("Se ha programado el mensaje para enviar");
    }
}
