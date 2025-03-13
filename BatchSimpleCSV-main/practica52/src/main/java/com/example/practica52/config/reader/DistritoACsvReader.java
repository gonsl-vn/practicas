package com.example.practica52.config.reader;

import com.example.practica52.model.Distrito;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DistritoACsvReader {


    @Bean
    public ItemReader<Distrito> csvDistritoReader(EntityManagerFactory entityManagerFactory){

        JpaPagingItemReader<Distrito> csvDReader = new JpaPagingItemReader<>();

        csvDReader.setEntityManagerFactory(entityManagerFactory);
        csvDReader.setQueryString("SELECT d FROM Distrito d");
        csvDReader.setPageSize(10);

        return  csvDReader;
    }
}
