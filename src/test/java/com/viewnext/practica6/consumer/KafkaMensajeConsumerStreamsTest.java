package com.viewnext.practica6.consumer;

import com.viewnext.practica6.model.Mensaje;
import org.apache.kafka.streams.kstream.ForeachAction;
import org.apache.kafka.streams.kstream.KStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

/**
 * The type Kafka mensaje consumer streams test.
 */
@ExtendWith(MockitoExtension.class)
class KafkaMensajeConsumerStreamsTest {

    @Mock
    private KStream<String, Mensaje> kStream;

    @InjectMocks
    private KafkaMensajeConsumerStreams kafkaMensajeConsumerStreams;

    /**
     * Should consume messages with kafka streams.
     */
    @Test
    void shouldConsumeMessagesWithKafkaStreams() {
        // Verifica que el foreach se llamó al crear la instancia
        verify(kStream, times(1)).foreach(any(ForeachAction.class));
    }
}
