package com.example.practica52.config;

import com.example.practica52.model.Calle;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class CalleWritter {

    private static Logger logger = LoggerFactory.getLogger(Slf4j.class);

    @Bean
    public ItemWriter<Calle> write(){
        return items -> {
            logger.info("Escribiendo en BD:" + items);
        };
    }
}
