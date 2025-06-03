package com.viewnext.practica6.consumer;

import com.viewnext.practica6.model.Mensaje;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

/**
 * The type Kafka mensaje consumer test.
 */
@ExtendWith(MockitoExtension.class)
class KafkaMensajeConsumerTest {

    @Spy
    @InjectMocks
    private KafkaMensajeConsumer consumer;

    /**
     * Test listen mensaje with mockito.
     */
    @Test
    void testListenMensaje() {
        Mensaje mensaje = new Mensaje();
        mensaje.setMessage("Contenido de prueba");

        consumer.listenMensaje(mensaje);

        // verify
        verify(consumer).listenMensaje(mensaje);
    }
}
