package com.viewnext.practica6.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * The type Kafka string producer.
 */
@Service
public class KafkaStringProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Enviar mensaje.
     *
     * @param mensaje
     *         the mensaje
     */
    // Enviamos el mensaje (String) al topic1
    public void enviarMensaje(String mensaje) {
        kafkaTemplate.send("topic1",
                mensaje); // Utilizamos la función send que viene de la clase KafkaTemplate en el que hay que pasarle como parámetros el topic y el mensaje
    }

}
