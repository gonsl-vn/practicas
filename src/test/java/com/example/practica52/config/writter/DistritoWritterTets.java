package com.example.practica52.config.writter;

import com.example.practica52.model.Distrito;
import com.example.practica52.repository.DistritoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DistritoWritterTets {
    @Autowired
    private DistritoWritter distritoWritter;
    @Autowired
    private DistritoRepository distritoRepository;
    @Autowired
    private ItemWriter<Distrito> writer;

    @BeforeEach
    void setUp(){
        distritoRepository.deleteAll();
    }

    @Test
    void distritoWritterTest_shouldSave2Distritos() throws Exception {
        Distrito distrito1 = Distrito.builder()
                .nombreDistrito("nombreDistrito1")
                .numeroViviendas(10)
                .build();
        Distrito distrito2 = Distrito.builder()
                .nombreDistrito("nombreDistrito2")
                .numeroViviendas(20)
                .build();
        List<Distrito> distritoList = List.of(distrito1, distrito2);

        writer= distritoWritter.distritoWrite();
        writer.write(new Chunk<>(distritoList));

        List<Distrito> resultadosList = distritoRepository.findAll();

        assertEquals(2,resultadosList.size());
        assertEquals(distrito1,resultadosList.get(0));
        assertEquals(distrito2, resultadosList.get(1));
    }
}
