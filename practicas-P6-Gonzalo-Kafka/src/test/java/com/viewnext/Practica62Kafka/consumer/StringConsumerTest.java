package com.viewnext.Practica62Kafka.consumer;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;

import static org.awaitility.Awaitility.await;
import static org.awaitility.Durations.FIVE_SECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static reactor.core.publisher.Mono.when;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = "stringTopic")
public class StringConsumerTest {


    @Spy
    private StringConsumer stringConsumer;
//Este test falla por el application.properties que tengo definido
// que el serializer y deserialzer de value sean de tipo json (para los mensajes)
// pero esto son Strings del ejercicio 1.
    @Test
    void stringConsumer_testConsumeString_ShouldLOGINFO(){
        String stringTest = "String test del consumer";
        stringConsumer.consumeString(stringTest);
       // when(stringConsumer.getLastMessage()).thenReturn(stringTest);

        await().atMost(FIVE_SECONDS).untilAsserted(()->{
            verify(stringConsumer).consumeString(stringTest);
            assertEquals(stringTest,stringConsumer.getLastMessage());
        });



    }
 }
