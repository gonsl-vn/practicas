package com.example.practica52.config.reader;

import com.example.practica52.config.BatchConfig;
import com.example.practica52.model.Calle;
import com.example.practica52.repository.CalleRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CalleACsvReaderTest {

    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @Autowired
    private CalleRepository calleRepository;
    @Autowired
    private CalleACsvReader calleACsvReader;

    private JpaPagingItemReader<Calle> reader;



    @BeforeEach
    void setUp() throws Exception {
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

        calleRepository.save(calle1);
        calleRepository.save(calle2);

        reader = (JpaPagingItemReader<Calle>) calleACsvReader.csvCalleReader(entityManagerFactory);

        reader.afterPropertiesSet();
        reader.open(new ExecutionContext());


    }

    @Test
    void calleCsvReaderTest_shouldRead2Calles() throws Exception {

        List<Calle> calles = new ArrayList<>();
        Calle calle;
        while((calle=reader.read())!=null){
            calles.add(calle);
        }
        assertEquals(2, calles.size());
        assertEquals("nombreCalle1", calles.get(0).getNombreCalle());
        assertEquals("nombreCalle2", calles.get(1).getNombreCalle());

    }

    @AfterEach
    void shutDown(){
        reader.close();
    }

/*
    private ItemReader<Calle> csvCalleReader(
            EntityManagerFactory entityManagerFactory){
        JpaPagingItemReader<Calle> csvCReader =
                new JpaPagingItemReader<>();
        csvCReader.setEntityManagerFactory(entityManagerFactory);
        csvCReader.setQueryString("SELECT c FROM CALLES c");

        csvCReader.setPageSize(10);
        return csvCReader;
    }*/
}
