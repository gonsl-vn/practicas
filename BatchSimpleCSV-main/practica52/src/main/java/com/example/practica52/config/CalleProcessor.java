package com.example.practica52.config;

import com.example.practica52.model.Calle;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class CalleProcessor {
    private static Logger logger = LoggerFactory.getLogger(Slf4j.class);
    @Bean
    public ItemProcessor<Calle, Calle> calleACalleProcessor(){
        logger.info("empezando procesamiento");
        return calle ->{
            if("ESTE".equals(calle.getNombreDistrito())){
                return calle;
            }else {return null;}

        };
    }
}
