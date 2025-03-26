package com.viewnext.springbatchf.processor;

import com.viewnext.springbatchf.model.Calle;
import org.springframework.batch.item.ItemProcessor;

public class CalleItemProcessor implements ItemProcessor<Calle, Calle> {

    public static final String filtroDistrito = "ESTE";

    public Calle process(Calle calle) throws Exception {

        if (calle.getNombreCalle() == null || calle.getNomDistrito() == null) {
            throw new IllegalArgumentException("Campo obligatorio nulo");
        }

        if (!filtroDistrito.equals(calle.getNomDistrito().trim())) {
            return null;
        }

        calle.setNombreCalle(calle.getNombreCalle().toUpperCase());
        return calle;
    }
}
