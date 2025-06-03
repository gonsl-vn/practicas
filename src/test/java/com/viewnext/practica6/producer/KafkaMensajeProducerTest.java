package com.viewnext.practica6.producer;

import com.viewnext.practica6.model.Mensaje;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.verify;

/**
 * The type Kafka mensaje producer test.
 */
@ExtendWith(MockitoExtension.class)
class KafkaMensajeProducerTest {

    @Mock
    private KafkaTemplate<String, Mensaje> kafkaTemplate;

    @InjectMocks
    private KafkaMensajeProducer producer;

    /**
     * Send mensaje should call kafka template send.
     */
    @Test
    void sendMensaje_shouldCallKafkaTemplateSend() {
        // given
        String topic = "topic1";
        Mensaje mensaje = new Mensaje();
        mensaje.setMessage("Mensaje de prueba");

        // when
        producer.sendMensaje(topic, mensaje);

        // then
        verify(kafkaTemplate).send(topic, mensaje);
    }
}
