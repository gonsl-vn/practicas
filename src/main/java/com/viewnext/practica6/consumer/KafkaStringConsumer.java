package com.viewnext.practica6.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * The type Kafka string consumer.
 */
@Service
public class KafkaStringConsumer {
    /**
     * Listen.
     *
     * @param mensaje
     *         the mensaje
     */
    // Función que nos permite obtener el String que enviamos como mensaje
    @KafkaListener(topics = "topic1", groupId = "kafka-group")
    public void listen(String mensaje) {
        System.out.println(
                "Mensaje recibido: " + mensaje); // Devolvemos un syso con el mensaje (String) que hemos enviado
    }

}
