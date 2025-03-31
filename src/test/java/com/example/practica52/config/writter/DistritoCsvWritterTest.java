package com.example.practica52.config.writter;

import com.example.practica52.model.Distrito;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DistritoCsvWritterTest {
    @Autowired
    private DistritoCsvWritter distritoCsvWritter;

    private File temporalFile;

    @AfterEach
    void shutDown(){

        temporalFile.delete();
    }



    @Test
    void distritoCsvWritterTest_shouldWrite2Distritos() throws Exception {
        Distrito distrito1 = Distrito.builder()
                .nombreDistrito("nombreDistrito1")
                .numeroViviendas(10)
                .build();
        Distrito distrito2 = Distrito.builder()
                .nombreDistrito("nombreDistrito2")
                .numeroViviendas(20)
                .build();
        List<Distrito> distritoList = List.of(distrito1, distrito2);

        FlatFileItemWriter<Distrito> writer = (FlatFileItemWriter<Distrito>) distritoCsvWritter.writeDistritoACsv();
        temporalFile = new File("src/test/resources/distritos_exportados_test.csv");

        writer.setResource(new FileSystemResource(temporalFile));
        writer.open(new ExecutionContext());

        writer.write(new Chunk<>(distritoList));

        long fileLines = Files.lines(temporalFile.toPath()).count();


        assertTrue(temporalFile.exists());
        assertEquals(3, fileLines);

        writer.close();

    }
}
