package com.viewnext.Practica62Kafka.producer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StringProducerTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;
    @InjectMocks
    private StringProducer stringProducer;

    @Test
    void stringProducer_testProduceString_shouldProduceMessageString(){
        String stringTest = "Esto es mensaje de testeo para String Producer";

        when(kafkaTemplate.send("stringTopic",stringTest)).thenReturn(null);

        stringProducer.produceString(stringTest);
        verify(kafkaTemplate).send("stringTopic", stringTest);
    }
}
