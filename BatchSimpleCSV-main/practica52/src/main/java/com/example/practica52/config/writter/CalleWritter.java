package com.example.practica52.config.writter;

import com.example.practica52.CalleRepository;
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
    private final CalleRepository calleRepository;

    //private static Logger logger = LoggerFactory.getLogger(Slf4j.class);

    public CalleWritter(CalleRepository calleRepository) {
        this.calleRepository = calleRepository;
    }

    @Bean
    public ItemWriter<Calle> calleCsvwriter(){
        log.info("Empezando escritura");
        return items -> {
            //logger.info("Escribiendo en BD:" + items);
            calleRepository.saveAll(items);
        };
    }
}
