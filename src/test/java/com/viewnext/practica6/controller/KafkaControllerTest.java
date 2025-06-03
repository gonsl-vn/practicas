package com.viewnext.practica6.controller;

import com.viewnext.practica6.producer.KafkaStringProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

/**
 * The type Kafka controller test.
 */
@ExtendWith(MockitoExtension.class)
class KafkaControllerTest {

    @Mock
    private KafkaStringProducer producer;

    @InjectMocks
    private KafkaController kafkaController;

    /**
     * Test enviar mensaje.
     */
    @Test
    void testEnviarMensaje() {
        // Given
        String mensaje = "Holaaaa";

        // When
        String resultado = kafkaController.enviarMensaje(mensaje);

        // Then
        verify(producer).enviarMensaje(mensaje);
        assertEquals("Mensaje enviado: Holaaaa", resultado);
    }
}
