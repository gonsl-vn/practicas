package com.example.practica52.config;

import com.example.practica52.CalleRepository;
import com.example.practica52.model.Distrito;
import com.example.practica52.repository.DistritoRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DistritoWritter {
    private static Logger logger = LoggerFactory.getLogger(Slf4j.class);
    private final DistritoRepository distritoRepository;

    public DistritoWritter(DistritoRepository distritoRepository) {
        this.distritoRepository = distritoRepository;
    }

    @Bean
    public ItemWriter<Distrito> distritoWrite(){
        logger.info("Empezando la escritura del distrito");
        return distrito ->{
            distritoRepository.saveAll(distrito);
        };
    }
}
