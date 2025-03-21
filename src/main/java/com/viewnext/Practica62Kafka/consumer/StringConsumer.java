package com.viewnext.Practica62Kafka.consumer;

import com.viewnext.Practica62Kafka.model.Mensaje;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StringConsumer {




    @KafkaListener(topics = "stringTopic", groupId = "stringConsumer" )
    public void consumeString(String mensaje){
        log.info("Se esta consumiendo El STRING" + mensaje);
    }
}
