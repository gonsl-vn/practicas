package com.example.practica52.config.reader;

import com.example.practica52.model.Calle;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MillonCalleReaderTest {

    @Autowired
    private MillonCalleReader millonCalleReader;

    private FlatFileItemReader<Calle> reader;

    @BeforeEach
    void setUp(){

        reader = millonCalleReader.millonCalleCsvReader();
        reader.open(new ExecutionContext());
    }

    @Test
    void millonCalleReaderTest_shouldRead3CallesFrom1mCsv() throws Exception {
        List<Calle> calleList = new ArrayList<>();

        Calle calle1 = reader.read();
        calleList.add(calle1);
        Calle calle2 = reader.read();
        calleList.add(calle2);
        Calle calle3 = reader.read();
        calleList.add(calle3);

        assertEquals(3, calleList.size());
        assertEquals("AARON", calleList.get(0).getNombreCalle());
        assertEquals("AARON", calleList.get(1).getNombreCalle());
        assertEquals("ABADIA SANTA ANA", calleList.get(2).getNombreCalle());
    }

    @AfterEach
    void shutDown(){
        reader.close();
    }
}
