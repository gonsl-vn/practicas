package com.viewnext.springbatchf.processor;

import com.viewnext.springbatchf.model.Calle;
import org.springframework.batch.item.ItemProcessor;

public class CalleItemProcessor implements ItemProcessor<Calle, Calle> {

    public Calle process(Calle calle) throws Exception {

        if (calle.getNombreCalle() == null || calle.getNomDistrito() == null) {
            throw new IllegalArgumentException("Campo obligatorio nulo");
        }

        //Filtro Zona
        //        if (!"ESTE".equals(calle.getNomDistrito().trim())) {
        //            return null;
        //        }

        calle.setNombreCalle(calle.getNombreCalle().toUpperCase());
        return calle;
    }
}
