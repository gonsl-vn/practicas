package com.viewnext.practica6.controller;

import com.viewnext.practica6.model.Mensaje;
import com.viewnext.practica6.producer.KafkaMensajeProducer;
import org.apache.kafka.streams.kstream.KStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

/**
 * The type Mensaje controller test.
 */
@ExtendWith(MockitoExtension.class)
class MensajeControllerTest {

    @Mock
    private KafkaMensajeProducer kafkaProducer;

    @Mock
    private KStream<String, Mensaje> kStream;

    @InjectMocks
    private MensajeController controller;

    /**
     * Send mensaje should send to kafka.
     */
    @Test
    void sendMensaje_shouldSendToKafka() {
        // given
        Mensaje mensaje = new Mensaje();
        mensaje.setMessage("Hola desde el test");

        // when
        var response = controller.sendMensaje(mensaje);

        // then
        verify(kafkaProducer).sendMensaje("topic1", mensaje);
        assertEquals("Mensaje enviado a Kafka", response.getBody());
    }

    /**
     * Stream mensajes should return flux.
     */
    @Test
    void streamMensajes_shouldReturnFlux() {
        // when
        Flux<Mensaje> flujo = controller.streamMensajes();

        // then
        assertNotNull(flujo);
    }
}
