package com.example.practica52.config.processor;

import com.example.practica52.model.Calle;
import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CalleACsvProcessorTest {

    @Autowired
    private ItemProcessor<Calle, Calle> csvCProcessor;

    @Test
    void calleACsvProcessorTest_shouldReturnTheSameCalle() throws Exception {
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

        Calle calleAnswer1 = csvCProcessor.process(calle1);
        Calle calleAnswer2 = csvCProcessor.process(calle2);

        assertEquals(calle1, calleAnswer1);
        assertEquals(calle2, calleAnswer2);
    }
}
