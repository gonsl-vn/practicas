package com.viewnext.practica6.controller;

import com.viewnext.practica6.producer.KafkaStringProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Kafka controller.
 */
@RestController
@RequestMapping("/api/kafka") // Ruta inicial: "/api/kafka"
public class KafkaController {

    @Autowired
    private KafkaStringProducer producer;

    /**
     * Enviar mensaje string.
     *
     * @param mensaje
     *         the mensaje
     * @return the string
     */
    // Método Post en el que nos permite enviar un string como mensaje. La ruta es: "/api/kafka/enviar"
    @PostMapping("/enviar")
    public String enviarMensaje(@RequestBody String mensaje) {
        producer.enviarMensaje(mensaje);
        return "Mensaje enviado: " + mensaje;
    }

}
