package com.viewnext.Practica62Kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StringProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void produceString(String mensaje){
        log.info("Se esta PRODUCIENDO el STRING: " + mensaje );
        kafkaTemplate.send("stringTopic", mensaje);
    }
}
