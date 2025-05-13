package com.viewnext.Practica62Kafka.controller;

import com.viewnext.Practica62Kafka.producer.StringProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class StringController {

    private final StringProducer stringProducer;

    public StringController(StringProducer stringProducer) {
        this.stringProducer = stringProducer;
    }

    @PostMapping("/enviaString")
    public void enviaString(@RequestBody String mensaje){
        log.info("Bien escrito" + mensaje);
        stringProducer.produceString(mensaje);
    }

}
