package com.viewnext.Practica62Kafka.consumer;

import com.viewnext.Practica62Kafka.model.Mensaje;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StringConsumer {

    public String lastMessage = "Aun no hay mensajes";
    private final ReactiveConsumer reactiveConsumer;

    public StringConsumer(ReactiveConsumer reactiveConsumer) {
        this.reactiveConsumer = reactiveConsumer;
    }

    @KafkaListener(topics = "stringTopic", groupId = "stringConsumer" )
    public void consumeString(String mensaje){
        log.info("Se esta consumiendo El STRING" + mensaje);
        lastMessage=mensaje;
        reactiveConsumer.emitirString(mensaje);
    }

    public String getLastMessage(){
        return lastMessage;
    }
}
