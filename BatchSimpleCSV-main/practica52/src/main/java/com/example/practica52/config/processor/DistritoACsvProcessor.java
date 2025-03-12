package com.example.practica52.config.processor;

import com.example.practica52.model.Distrito;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class DistritoACsvProcessor {
    private final Logger logger = LoggerFactory.getLogger(Slf4j.class);


    @Bean
    public ItemProcessor<Distrito, Distrito> csvDProcessor(){
        return distrito->{
            logger.info("procesando datos de distritos...");
                    return distrito;
        };
    }
}
