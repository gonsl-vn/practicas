package com.example.practica52.config.processor;

import com.example.practica52.model.Calle;
import com.example.practica52.model.Distrito;
import com.example.practica52.repository.DistritoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DistritoProcessorTest {

    @Autowired
    private DistritoProcessor distritoProcessor;
    @Autowired
    private ItemProcessor<Calle, Distrito> distritoNumeroCalleProcessor;
    @Autowired
    private DistritoRepository distritoRepository;

   @AfterEach
   void shutDown(){
       distritoRepository.deleteAll();
   }

    @Test
    void distritoProcessorTest_shouldCreateDistritoFromCalle() throws Exception{
        Calle calle1 = Calle.builder()
                .codigoCalle(1)
                .nombreCalle("nombreCalle1")
                .barrio("barrio1")
                .codigoDistrito(11)
                .nombreDistrito("distrito1")
                .primerNumTramo(1)
                .ultimoNumTramo(5)
                .tipoVia("calle")
                .build();
        Calle calle2 = Calle.builder()
                .codigoCalle(2)
                .nombreCalle("nombreCalle2")
                .barrio("barrio2")
                .codigoDistrito(22)
                .nombreDistrito("distrito2")
                .primerNumTramo(2)
                .ultimoNumTramo(6)
                .tipoVia("calle")
                .build();
        Distrito distrito1 = Distrito.builder()
                .id(1)
                .numeroViviendas(2)
                .nombreDistrito("distrito1")
                .build();
        Distrito distrito2 = Distrito.builder()
                .id(2)
                .numeroViviendas(2)
                .nombreDistrito("distrito2")
                .build();

        //Preguntar porque si Comparo Distrito1 y distritoAnswer/Saved1 da error aunque sea el mismo objeto cambia el Distrito@numerosyletras, pero mismo id y atributos
        Distrito distritoAnswer1 = distritoNumeroCalleProcessor.process(calle1);
        Distrito distritoAnswer2 = distritoNumeroCalleProcessor.process(calle2);

        Distrito distritoSaved1 = distritoRepository.save(distritoAnswer1);
        Distrito distritoSaved2 = distritoRepository.save(distritoAnswer2);

        assertEquals(distrito1.getNombreDistrito(), distritoSaved1.getNombreDistrito());
        assertEquals(distrito2.getNombreDistrito(), distritoSaved2.getNombreDistrito());

        assertEquals(distrito1.getNumeroViviendas(), distritoSaved1.getNumeroViviendas());
        assertEquals(distrito2.getNumeroViviendas(),distritoSaved2.getNumeroViviendas());

        assertEquals(distrito1.getId(),distritoSaved1.getId());
        assertEquals(distrito2.getId(),distritoSaved2.getId());
    }
}
