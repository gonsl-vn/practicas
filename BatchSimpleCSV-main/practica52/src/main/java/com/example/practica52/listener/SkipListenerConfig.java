package com.example.practica52.listener;

import com.example.practica52.model.Calle;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.batch.core.SkipListener;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class SkipListenerConfig implements SkipListener<Calle, Calle> {

    private static Logger logger = LoggerFactory.getLogger(Slf4j.class);

    @Override
    public void onSkipInRead( Throwable throwable){
        logger.warn("Error de lectura  en " + throwable.getMessage());
    }

    @Override
    public void onSkipInProcess(Calle calle, Throwable  throwable){
        logger.warn("Error en el procesamiento de: " + calle +
                throwable.getMessage());
    }

    @Override
    public void onSkipInWrite(Calle calle, Throwable throwable){
        logger.warn("Error en la escritura de: " + calle +
                throwable.getMessage());
    }
}
