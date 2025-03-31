package com.example.practica52.config.writter;

import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
public class StringUsuACsvWritterTest {
    @Autowired
    private StringUsuACsvWritter stringUsuACsvWritter;

    private File temporalFile;

    private FlatFileItemWriter<String> writer;

    @BeforeEach
    void setUp(){
        writer = (FlatFileItemWriter<String>) stringUsuACsvWritter.stringCsvWritter();

        temporalFile = new File("src/test/resources/string_compra_usuarios_test.csv");

        writer.setResource(new FileSystemResource(temporalFile));
    }

    @AfterEach
    void shutDown(){
        writer.close();
        temporalFile.delete();
    }

    @Test
    void stringUsuACsvWritterTest_shouldCreateFileWithStringLines() throws Exception {
        String compra1 = "El/La Sr/a Juan Pérezcon Dni" +
                " 12345678A residente en Calle Falsa 123,\n"
                + " con codigo postal 28001,\n" +
                " ha realizado una compra de 100.0 euros\n"
                + "con numero de pedido 1.";
        String compra2 = "El/La Sr/a María Garcíacon Dni" +
                " 23456789B residente en Calle Real 456,\n"
                + " con codigo postal 8001,\n" +
                " ha realizado una compra de 200.0 euros\n"
                + "con numero de pedido 2.";
        List<String> stringList = List.of(compra1,compra2);

        writer.open(new ExecutionContext());
        writer.write(new Chunk<>(stringList));

        long fileLines = Files.lines(temporalFile.toPath()).count();


        assertTrue(temporalFile.exists());
        assertEquals(8, fileLines);

    }
}
