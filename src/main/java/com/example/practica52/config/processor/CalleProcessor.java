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
import org.springframework.stereotype.Component;

@Component
@Slf4j
@StepScope
public class CalleProcessor implements ItemProcessor<Calle, Calle> {
    @Value("#{jobParameters['distritoAFiltrar']}")
    private String distritoAFiltrar;

    @Override
    public Calle process(Calle calle){
        //log.info("Procesando calle:" + calle.getNombreCalle());
        //log.info("Con el distrito pasado como param: " + distritoAFiltrar);
        if (distritoAFiltrar==null ||
                distritoAFiltrar.equalsIgnoreCase(calle.getNombreDistrito())
                ){
            return calle;
        } return null;}

    //private static final Logger logger = LoggerFactory.getLogger(Slf4j.class);

/*
    @Component
    @StepScope
    public ItemProcessor<Calle, Calle> calleACalleProcessor(@Value("#{jobParameters['distritoAFiltrar']}")
    String distritoAFiltrar){
        log.info("empezando procesamiento");
        return new ItemProcessor<Calle, Calle>() {
            @Override
            public Calle process(Calle calle) {
                if(distritoAFiltrar==null ||
                calle.getNombreDistrito().equalsIgnoreCase(distritoAFiltrar)){
                    return  calle;
                }return null;
            }
        };
  */      /*return calle ->{
            if(distritoAFiltrar == null ||
            calle.getNombreDistrito().equalsIgnoreCase(distritoAFiltrar)){
                return calle;
            }
        return null;
        };*/

}
