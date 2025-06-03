package com.viewnext.practica6.producer;

import com.viewnext.practica6.model.Mensaje;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * The type Kafka mensaje producer.
 */
@Service
public class KafkaMensajeProducer {

    private final KafkaTemplate<String, Mensaje> kafkaTemplate;

    /**
     * Instantiates a new Kafka mensaje producer.
     *
     * @param kafkaTemplate
     *         the kafka template
     */
    public KafkaMensajeProducer(KafkaTemplate<String, Mensaje> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Send mensaje.
     *
     * @param topic
     *         the topic
     * @param mensaje
     *         the mensaje
     */
    // Función que nos permite enviar un mensaje, en este caso el objeto Mensaje
    public void sendMensaje(String topic, Mensaje mensaje) {
        kafkaTemplate.send(topic,
                mensaje); // Utilizamos la función send, que viene de la clase KafkaTemplate, en el que hay que pasarle el topic y el mensaje
    }
}
