package com.viewnext.practica6.consumer;

import com.viewnext.practica6.model.Mensaje;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.stereotype.Service;

/**
 * The type Kafka mensaje consumer streams.
 */
@Service
public class KafkaMensajeConsumerStreams {
    /**
     * Instantiates a new Kafka mensaje consumer streams.
     *
     * @param kStream
     *         the k stream
     */
    // Función que nos permite obtener el mensaje que hemos enviado a partir del stream
    public KafkaMensajeConsumerStreams(KStream<String, Mensaje> kStream) {
        kStream.foreach((key, mensaje) -> {
            System.out.println(
                    "Mensaje recibido con KafkaStreams: " + mensaje); // Devolvemos un syso con el mensaje que hemos enviado
        });
    }
}
