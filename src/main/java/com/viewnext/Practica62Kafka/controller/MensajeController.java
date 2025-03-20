package com.viewnext.Practica62Kafka.controller;

import com.viewnext.Practica62Kafka.model.MensajeConNombreUsu;
import com.viewnext.Practica62Kafka.producer.MensajeProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MensajeController {

    private final MensajeProducer mensajeProducer;

    @PostMapping("/enviaMensaje")
    public ResponseEntity<String> enviaMensaje(
            @RequestBody MensajeConNombreUsu mensaje){

        mensajeProducer.convertirAMensajeYEnviar(mensaje);
        return  ResponseEntity.ok("Se ha programado el mensaje para enviar");
    }
}
