package com.example.practica52.config.reader;

import com.example.practica52.model.Distrito;
import com.example.practica52.repository.DistritoRepository;
import jakarta.persistence.EntityManagerFactory;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DistritoACsvReaderTest {
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @Autowired
    private DistritoRepository distritoRepository;
    @Autowired
    private DistritoACsvReader distritoACsvReader;

    private JpaPagingItemReader<Distrito> reader;

    @BeforeEach
    void  setUp() throws Exception {
        Distrito distrito1 = Distrito.builder()
                .nombreDistrito("nombreDistrito1")
                .numeroViviendas(10)
                .build();
        Distrito distrito2 = Distrito.builder()
                .nombreDistrito("nombreDistrito2")
                .numeroViviendas(20)
                .build();

        distritoRepository.save(distrito1);
        distritoRepository.save(distrito2);

        reader= (JpaPagingItemReader<Distrito>) distritoACsvReader.csvDistritoReader(entityManagerFactory);

        reader.afterPropertiesSet();
        reader.open(new ExecutionContext());
    }

    @Test
    void DistritoACsvReaderTest_shouldRead2Distritos() throws Exception {

        List<Distrito> distritosList = new ArrayList<>();
        Distrito distrito;

        while((distrito=reader.read())!=null){
            distritosList.add(distrito);
        }

        assertEquals(2,distritosList.size());
        assertEquals("nombreDistrito1", distritosList.get(0).getNombreDistrito());
        assertEquals("nombreDistrito2", distritosList.get(1).getNombreDistrito());
    }

    @AfterEach
    void shutDown(){
        distritoRepository.deleteAll();
        reader.close();

    }

}
