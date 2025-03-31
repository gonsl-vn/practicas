package com.example.practica52.config.processor;

import com.example.practica52.model.Distrito;
import com.example.practica52.repository.DistritoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DistritoACsvProcessorTest {

    @Autowired
    private ItemProcessor<Distrito, Distrito> csvDProcessor;
    @Autowired
    private DistritoRepository distritoRepository;


    @AfterEach
    void shutDown(){
        distritoRepository.deleteAll();
    }

    @Test
    void distritoACsvProcessorTest_shouldReturnTheSameDistrito() throws Exception {

        Distrito distrito1 = Distrito.builder()
                .nombreDistrito("nombreDistrito1")
                .numeroViviendas(10)
                .build();
        Distrito distrito2 = Distrito.builder()
                .nombreDistrito("nombreDistrito2")
                .numeroViviendas(20)
                .build();

        Distrito distritoAnswer1 = csvDProcessor.process(distrito1);
        Distrito distritoAnswer2 = csvDProcessor.process(distrito2);

        assertEquals(distrito1,distritoAnswer1);
        assertEquals(distrito2,distritoAnswer2);
    }
}
