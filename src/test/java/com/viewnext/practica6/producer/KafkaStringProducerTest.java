package com.viewnext.practica6.producer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.verify;

/**
 * The type Kafka string producer test.
 */
@ExtendWith(MockitoExtension.class)
class KafkaStringProducerTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private KafkaStringProducer producer;

    /**
     * Enviar mensaje should send string to kafka.
     */
    @Test
    void enviarMensaje_shouldSendStringToKafka() {
        // given
        String mensaje = "Mensaje de prueba";

        // when
        producer.enviarMensaje(mensaje);

        // then
        verify(kafkaTemplate).send("topic1", mensaje);
    }
}
