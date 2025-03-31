package com.example.practica52.config.writter;

import com.example.practica52.model.Calle;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CalleACsvWritterTest {

    @Autowired
    private CalleACsvWritter calleACsvWritter;


    private File temporalFile;
    @AfterEach
    void shutDown(){
        if(temporalFile.exists()){
            temporalFile.delete();
        }
    }

    @Test
    void calleACsvWritter_shouldWrite2Lines() throws Exception {
        FlatFileItemWriter<Calle> writer = (FlatFileItemWriter<Calle>) calleACsvWritter.writeCalleACsv();
        temporalFile= new File("src/test/resources/calles_exportadas_test.csv");
        //writer = calleACsvWritter.writeCalleACsv();
        writer.setResource(new FileSystemResource("src/test/resources/calles_exportadas_test.csv"));
        writer.open(new ExecutionContext());
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

        long fileLines = Files.lines(temporalFile.toPath()).count();

        assertTrue(temporalFile.exists());
        assertEquals(3, fileLines);

        writer.close();
    }

}
