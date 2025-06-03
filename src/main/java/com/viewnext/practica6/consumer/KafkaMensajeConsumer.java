package com.viewnext.practica6.consumer;

import com.viewnext.practica6.model.Mensaje;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * The type Kafka mensaje consumer.
 */
@Service
public class KafkaMensajeConsumer {

    /**
     * Listen mensaje.
     *
     * @param mensaje
     *         the mensaje
     */
    // Función para ver el mensaje que enviamos, al topic1
    @KafkaListener(topics = "topic1", groupId = "kafka-group", containerFactory = "kafkaListenerContainerFactory")
    public void listenMensaje(Mensaje mensaje) {
        System.out.println("Mensaje recibido: " + mensaje); // Devolvemos un syso con el mensaje que hemos enviado
    }

}
