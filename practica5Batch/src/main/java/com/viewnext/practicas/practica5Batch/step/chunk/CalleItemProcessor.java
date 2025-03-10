package com.viewnext.practicas.practica5Batch.step.chunk;

import com.viewnext.practicas.practica5Batch.model.Calle;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class CalleItemProcessor implements ItemProcessor<Calle, Calle> {
    @Override
    public Calle process(Calle calle){
        System.out.println("Se estan procesando la calle: " + calle.getNombreCalle());
        return calle;

        //return "CENTRO".equals(calle.getNombreDistrito())? calle : null;
    }
}
