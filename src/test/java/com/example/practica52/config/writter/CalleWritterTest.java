package com.example.practica52.config.writter;

import com.example.practica52.model.Calle;
import com.example.practica52.repository.CalleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CalleWritterTest {
    @Autowired
    private CalleWritter calleWritter;

    @Autowired
    private CalleRepository calleRepository;
    @Autowired
    @Qualifier("calleCsvwriter")
    private ItemWriter<Calle> writer;

    @BeforeEach
    void setUp(){
        calleRepository.deleteAll();
    }

    @Test
    void calleWritterTest_shouldSave2Calles() throws Exception {
        writer=calleWritter.calleCsvwriter();
        Calle calle1 = Calle.builder()
                .codigoCalle(1)
                .nombreCalle("nombreCalle1")
                .barrio("barrio1")
                .codigoDistrito(11)
                .nombreDistrito("distrito1")
                .primerNumTramo(1)
                .ultimoNumTramo(3)
                .tipoVia("calle")
                .build();
        Calle calle2 = Calle.builder()
                .codigoCalle(2)
                .nombreCalle("nombreCalle2")
                .barrio("barrio2")
                .codigoDistrito(22)
                .nombreDistrito("distrito2")
                .primerNumTramo(2)
                .ultimoNumTramo(4)
                .tipoVia("calle")
                .build();
        List<Calle> calleList = List.of(calle1,calle2);

        writer.write(new Chunk<>(calleList));

        List<Calle> resultadosList = calleRepository.findAll();

        assertEquals(2, resultadosList.size());
        assertEquals(calle1,resultadosList.get(0));
        assertEquals(calle2, resultadosList.get(1));
    }
}
