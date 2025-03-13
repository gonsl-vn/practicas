package com.example.practica52.config.reader;

import com.example.practica52.model.Calle;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;

public class CalleACsvReader {

    @Bean
    public ItemReader<Calle> csvCalleReader(
            EntityManagerFactory entityManagerFactory){
        JpaPagingItemReader<Calle> csvCReader =
                new JpaPagingItemReader<>();
        csvCReader.setEntityManagerFactory(entityManagerFactory);
        csvCReader.setQueryString("SELECT c FROM CALLES c");

        csvCReader.setPageSize(10);
        return csvCReader;
    }

}
