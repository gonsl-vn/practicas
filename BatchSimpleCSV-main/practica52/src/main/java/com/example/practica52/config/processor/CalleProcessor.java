package com.example.practica52.config.processor;

import com.example.practica52.model.Calle;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class CalleProcessor {

    //private static final Logger logger = LoggerFactory.getLogger(Slf4j.class);


    @Bean
    @StepScope
    public ItemProcessor<Calle, Calle> calleACalleProcessor(@Value("#{jobParameters['distritoAFiltrar']}")
    String distritoAFiltrar){
        log.info("empezando procesamiento");
        return calle ->{
            if(distritoAFiltrar == null ||
            calle.getNombreDistrito().equalsIgnoreCase(distritoAFiltrar)){
                return calle;
            }
        return null;
        };
    }
}
